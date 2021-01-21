package com.example.ratatouille.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.models.restoDetailModel;
import com.example.ratatouille.utils.detailPhotoAdapter;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.utils.restoDetailCallbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.restaurantDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class restoDetailController {

    static String resto_id;
    static String resto_name;
    static String location;
    static String resto_type;
    static double rating;
    static String jam_buka;
    static String average_price;
    static String menu_url;
    static String phone;
    static PagerAdapter photoAdapter;
    static ArrayList<detailPhotoModels> photoList;

    public static restoDetailCallbackHelper rdch = new restoDetailCallbackHelper() {
        @Override
        public void onLoad(Context context, Intent intent, PagerAdapter pager) {
            VariablesUsed.currentRestoDetail = new restoDetailModel(resto_id, resto_name, location, resto_type, average_price, jam_buka, menu_url, rating, phone, null);
            context.startActivity(intent);
        }
    };

    public static void query(Context context, Intent intent, String id) {
        String qParam = String.format("restaurant?res_id=" + id);
        String url = String.format(VariablesUsed.API_BASE_URL + qParam);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    getValue(context, intent, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", VariablesUsed.API_KEY);
                return headers;
            }
        };

        // Access the RequestQueue through your requestMaker
        requestMaker.getInstance(context).addToRequestQueue(req);
    }

    public static void getValue(Context context, Intent intent, JSONObject object) throws JSONException {
        JSONObject resto = object;

        resto_id = resto.getString("id");
        resto_name = resto.getString("name");
        location = resto.getJSONObject("location").getString("locality");
        resto_type = resto.getString("cuisines");
        rating = resto.getJSONObject("user_rating").getDouble("aggregate_rating");
        jam_buka = resto.getString("timings");
        average_price = resto.getString("average_cost_for_two");
        menu_url = resto.getString("menu_url");
        phone = resto.getString("phone_numbers");

//        load_photo(context, intent, resto_id);
        rdch.onLoad(context, intent, photoAdapter);
    }

    private static void load_photo(Context context, Intent intent, String id) {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Restaurants").child(id).child("photos");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collectPhotos(context, snapshot, intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static void collectPhotos(Context context, DataSnapshot items, Intent intent) {
        photoList = new ArrayList<>();

        ArrayList<String> photoArray = (ArrayList) items.getValue();
        //iterate through each items
        for (int i=0; i<photoArray.size(); i++){
            if(i == 0) continue;
            //Get photo field and append to list
            photoList.add(new detailPhotoModels((String) photoArray.get(i)));
        }

        photoAdapter = new detailPhotoAdapter(context, photoList);
        rdch.onLoad(context, intent, photoAdapter);
    }

}
