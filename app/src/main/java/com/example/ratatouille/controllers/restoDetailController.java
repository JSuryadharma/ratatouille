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
    static PagerAdapter photoAdapter;
    static ArrayList<detailPhotoModels> photoList;

    public static restoDetailCallbackHelper rdch = new restoDetailCallbackHelper() {
        @Override
        public void onLoad(Context context, Intent intent, PagerAdapter pager) {
            VariablesUsed.currentRestoDetail = new restoDetailModel(resto_id, resto_name, location, resto_type, average_price, jam_buka, menu_url, rating, photoAdapter);
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

//        title.setText(resto_name);
//        type.setText(resto_type);
//        address.setText(location);
//        timings.setText(jam_buka);
//        avg.setText(average_price);
//        menu.setText(menu_url);
//        ratingBar.setRating((float) rating);
        load_photo(context, intent, resto_id);
    }

    private static void load_photo(Context context, Intent intent, String id) {
        StorageReference images = DatabaseHelper.getStorage().getReference()
                .child("restaurants/" + id + "/photos");

        images.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        photoList = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            final long ONE_MEGABYTE = 1024 * 1024;
                            item.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    photoList.add(new detailPhotoModels(bitmap));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    System.out.println(exception);
                                }
                            });
                        }
                        photoAdapter = new detailPhotoAdapter(context, photoList);

                        rdch.onLoad(context, intent, photoAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
    }

}
