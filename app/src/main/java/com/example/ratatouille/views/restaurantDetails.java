package com.example.ratatouille.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.FavouritesController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.models.menuPhotoModels;
import com.example.ratatouille.utils.detailPhotoAdapter;
import com.example.ratatouille.utils.menuPhotoAdapter;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class restaurantDetails extends AppCompatActivity {

    private SwipeRefreshLayout pullToRefresh;
    private ViewPager photoView, menuView;
    private TextView title, type, timings, address, avg, reviewButton;
    private RatingBar ratingBar;
    private LinearLayout bookNowButton, backButton, addToFavButton;
    private Context context;
    private ArrayList<detailPhotoModels> photoList;
    private ArrayList<menuPhotoModels> menuList;
    private PagerAdapter photoAdapter, menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        title = findViewById(R.id.resto_title);
        type = findViewById(R.id.resto_type);
        timings = findViewById(R.id.resto_timing);
        address = findViewById(R.id.resto_address);
        avg = findViewById(R.id.resto_avg_price);
        ratingBar = findViewById(R.id.ratingBarRestoDetails);
        photoView = findViewById(R.id.detailPhoto_viewpager);
        menuView = findViewById(R.id.menuPhoto_viewpager);
        bookNowButton = findViewById(R.id.bookNowButton);
        backButton = findViewById(R.id.detail_backButton);
        pullToRefresh = findViewById(R.id.detail_pulltorefresh);
        reviewButton = findViewById(R.id.reviewButton);
        addToFavButton = findViewById(R.id.addToFavouriteButton);
        context = this;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                backToSearch(context);
            }
        });

        addToFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouritesController.addFavourite(context, VariablesUsed.currentRestoDetail.getResto_name() + "-" + VariablesUsed.currentRestoDetail.getLocation(),
                        VariablesUsed.currentRestoDetail.getResto_type(), VariablesUsed.currentRestoDetail.getAverage_price(),
                        VariablesUsed.currentRestoDetail.getRating(), VariablesUsed.loggedUser.getUid()+"-"+VariablesUsed.currentRestoDetail.getResto_id(),
                        VariablesUsed.loggedUser.getUid(), VariablesUsed.DEFAULT_PHOTO);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                Intent mainMenuIntent = new Intent(context, reviewPage.class);
                startActivity(mainMenuIntent);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                loadPhotos(context, VariablesUsed.currentRestoDetail.getResto_id());
                loadMenus(context, VariablesUsed.currentRestoDetail.getResto_id());
                MediaPlayer player = MediaPlayer.create(context, R.raw.open);
                player.start();
                pullToRefresh.setRefreshing(false);
            }
        });

        loadData();

        loadPhotos(context, VariablesUsed.currentRestoDetail.getResto_id());
        loadMenus(context, VariablesUsed.currentRestoDetail.getResto_id());
    }

    private void loadData() {
        title.setText(VariablesUsed.currentRestoDetail.getResto_name());
        type.setText(VariablesUsed.currentRestoDetail.getResto_type());
        timings.setText(VariablesUsed.currentRestoDetail.getJam_buka());
        address.setText(VariablesUsed.currentRestoDetail.getLocation());
        avg.setText(VariablesUsed.currentRestoDetail.getAverage_price());
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

    private void loadMenus(Context context, String id) {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Restaurants").child(id).child("menus");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collectMenus(context, snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void collectMenus(Context context, DataSnapshot items) {
        menuList = new ArrayList<menuPhotoModels>();

        ArrayList<String> photoArray = (ArrayList) items.getValue();

        if(photoArray == null) {
            menuList.add(new menuPhotoModels(VariablesUsed.DEFAULT_PHOTO_FOOD));
            this.menuAdapter = new menuPhotoAdapter(context, menuList);
            this.menuView.setAdapter(this.menuAdapter);
            VariablesUsed.currentRestoDetail.setMenuAdapter(menuAdapter);
            return;
        }

        //iterate through each items
        for (int i=1; i<photoArray.size(); i++){
            //Get photo field and append to list
            menuList.add(new menuPhotoModels((String) photoArray.get(i)));
        }

        this.menuAdapter = new menuPhotoAdapter(context, menuList);
        this.menuView.setAdapter(this.menuAdapter);
        VariablesUsed.currentRestoDetail.setMenuAdapter(menuAdapter);
    }

    private void loadPhotos(Context context, String id) {
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
        if(VariablesUsed.previousState != null) {
            VariablesUsed.previousState = null;
            Intent mainMenuIntent = new Intent(context, customerView.class);
            startActivity(mainMenuIntent);
            finish();
        }
        else {
            Intent searchIntent = new Intent(context, Search.class);
            Bundle bundle = new Bundle();
            bundle.putString("q", title.getText().toString());
            searchIntent.putExtras(bundle);
            startActivity(searchIntent);
            finish();
        }
    }
}