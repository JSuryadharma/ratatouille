package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Review {
    private String reviewID;
    private String restaurantID;
    private String customerID;
    private Double maskRate, temperatureRate, sanitizeRate, socialDistancingRate, physicalBarriersRate;
    private String description;

    private static Review selectedValues = null;

    public Review() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Review(String reviewID, String restaurantID, String customerID, Double maskRate, Double temperatureRate, Double sanitizeRate, Double socialDistancingRate, Double physicalBarriersRate, String description) {
        this.reviewID = reviewID;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.maskRate = maskRate;
        this.temperatureRate = temperatureRate;
        this.sanitizeRate = sanitizeRate;
        this.socialDistancingRate = socialDistancingRate;
        this.physicalBarriersRate = physicalBarriersRate;
        this.description = description;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewTable.REVIEW_TABLE);
        dbRef.child(reviewID).setValue(this);
    }

    public Review update(Double maskRate, Double temperatureRate, Double sanitizeRate, Double socialDistancingRate, Double physicalBarriersRate, String description){
        this.maskRate = maskRate;
        this.temperatureRate = temperatureRate;
        this.sanitizeRate = sanitizeRate;
        this.socialDistancingRate = socialDistancingRate;
        this.physicalBarriersRate = physicalBarriersRate;
        this.description = description;

        save();

        return this;
    }

    public static Review get(String reviewID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewTable.REVIEW_TABLE);
        selectedValues = null;
        dbRef.child(reviewID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Review.class);
                Log.w(TAG, "onSuccess: Review retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Review retrival failed!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<Review> getAll(String restaurantID){ // Review for specific restaurant
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewTable.REVIEW_TABLE);
        ArrayList<Review> reviewList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){ // setiap child dari snapshot json
                    if(eachData.child("restaurantID").getValue().equals(restaurantID)) {
                        Review curReview = eachData.getValue(Review.class);
                        reviewList.add(curReview);
                    }
                }
                Log.w(TAG, "onSuccess: All Review retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Review retrival failed!");
            }
        });
        return reviewList;
    }
}
