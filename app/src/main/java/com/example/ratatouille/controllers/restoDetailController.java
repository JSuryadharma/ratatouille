package com.example.ratatouille.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.models.restoDetailModel;
import com.example.ratatouille.utils.detailPhotoAdapter;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.utils.restoDetailCallbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.restaurantDetails;
import com.github.chrisbanes.photoview.PhotoView;
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

    public static restoDetailCallbackHelper rdch = new restoDetailCallbackHelper() {
        @Override
        public void onLoad(Context context, Intent intent) {
            VariablesUsed.currentRestoDetail = new restoDetailModel(resto_id, resto_name, location, resto_type, average_price, jam_buka, menu_url, rating, phone, null, null);
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

        rdch.onLoad(context, intent);
    }

    public static void showDialogMessage(String url, Context context){
        Dialog showDialog = new Dialog(context);
        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.setContentView(R.layout.dialog_photo);

//        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        PhotoView zoomPhoto = showDialog.findViewById(R.id.zoomPhoto);
        ImageView zoomBackButton = showDialog.findViewById(R.id.zoomBackButton);
        Glide.with(showDialog.getContext()).load(url).into(zoomPhoto);

        zoomBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer play = MediaPlayer.create(context, R.raw.open);
                play.start();
                showDialog.dismiss();
            }
        });

        showDialog.show();
    }
}
