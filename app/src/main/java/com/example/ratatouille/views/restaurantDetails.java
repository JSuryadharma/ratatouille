package com.example.ratatouille.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.utils.detailPhotoAdapter;
import com.example.ratatouille.utils.restoDetailCallbackHelper;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class restaurantDetails extends AppCompatActivity {

    private ViewPager photoView;
    private TextView title, type, timings, address, avg, menu;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        title = findViewById(R.id.resto_title);
        type = findViewById(R.id.resto_type);
        timings = findViewById(R.id.resto_timing);
        address = findViewById(R.id.resto_address);
        avg = findViewById(R.id.resto_avg_price);
        menu = findViewById(R.id.resto_menu);
        ratingBar = findViewById(R.id.ratingBarRestoDetails);
        photoView = findViewById(R.id.detailPhoto_viewpager);

        loadData();
    }

    private void loadData() {
        title.setText(VariablesUsed.currentRestoDetail.getResto_name());
        type.setText(VariablesUsed.currentRestoDetail.getResto_type());
        timings.setText(VariablesUsed.currentRestoDetail.getJam_buka());
        address.setText(VariablesUsed.currentRestoDetail.getLocation());
        avg.setText(VariablesUsed.currentRestoDetail.getAverage_price());
        menu.setText(VariablesUsed.currentRestoDetail.getMenu_url());
        ratingBar.setRating((float) VariablesUsed.currentRestoDetail.getRating());
        photoView.setAdapter(VariablesUsed.currentRestoDetail.getPhotoAdapter());
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }

}