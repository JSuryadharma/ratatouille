package com.example.ratatouille.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

public class restaurantDetails extends AppCompatActivity {

    private ViewPager photoView;
    private TextView title, type, timings, address, avg, menu;
    private RatingBar ratingBar;
    private LinearLayout bookNowButton, backButton;
    private Context context;
    private ArrayList<detailPhotoModels> photoList;
    private PagerAdapter photoAdapter;

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
        bookNowButton = findViewById(R.id.bookNowButton);
        backButton = findViewById(R.id.detail_backButton);
        context = this;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                backToSearch(context);
            }
        });

        loadData();

        load_photo(context, VariablesUsed.currentRestoDetail.getResto_id());
    }

    private void loadData() {
        title.setText(VariablesUsed.currentRestoDetail.getResto_name());
        type.setText(VariablesUsed.currentRestoDetail.getResto_type());
        timings.setText(VariablesUsed.currentRestoDetail.getJam_buka());
        address.setText(VariablesUsed.currentRestoDetail.getLocation());
        avg.setText(VariablesUsed.currentRestoDetail.getAverage_price());
        menu.setText(VariablesUsed.currentRestoDetail.getMenu_url());
        ratingBar.setRating((float) VariablesUsed.currentRestoDetail.getRating());
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+VariablesUsed.currentRestoDetail.getPhone()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }

    private void load_photo(Context context, String id) {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Restaurants").child(id).child("photos");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collectPhotos(context, snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void collectPhotos(Context context, DataSnapshot items) {
        photoList = new ArrayList<detailPhotoModels>();

        ArrayList<String> photoArray = (ArrayList) items.getValue();

        if(photoArray == null) {
            photoList.add(new detailPhotoModels(VariablesUsed.DEFAULT_PHOTO_CAMERA));
            this.photoAdapter = new detailPhotoAdapter(context, photoList);
            this.photoView.setAdapter(this.photoAdapter);
            VariablesUsed.currentRestoDetail.setPhotoAdapter(photoAdapter);
            return;
        }

        //iterate through each items
        for (int i=1; i<photoArray.size(); i++){
            //Get photo field and append to list
            photoList.add(new detailPhotoModels((String) photoArray.get(i)));
        }

        this.photoAdapter = new detailPhotoAdapter(context, photoList);
        this.photoView.setAdapter(this.photoAdapter);
        VariablesUsed.currentRestoDetail.setPhotoAdapter(photoAdapter);
    }

    private void backToSearch(Context context) {
        Intent searchIntent = new Intent(context, Search.class);
        Bundle bundle = new Bundle();
        bundle.putString("q", title.getText().toString());
        searchIntent.putExtras(bundle);
        startActivity(searchIntent);
        finish();
    }
}