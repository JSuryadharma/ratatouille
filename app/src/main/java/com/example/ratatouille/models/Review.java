package com.example.ratatouille.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Review {
    private String reviewID;
    private String restaurantID;
    private String customerID;
    private String customerUsername; //denormalization..
    private Double maskRate, temperatureRate, sanitizeRate, socialDistancingRate, physicalBarriersRate;
    private String description;
    private String submitDate;

    private static Review selectedValues = null;

    public Review() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Review(String reviewID, String restaurantID, String customerID, String customerUsername, Double maskRate, Double temperatureRate, Double sanitizeRate, Double socialDistancingRate, Double physicalBarriersRate, String description) {
        this.reviewID = reviewID;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.customerUsername = customerUsername;
        this.maskRate = maskRate;
        this.temperatureRate = temperatureRate;
        this.sanitizeRate = sanitizeRate;
        this.socialDistancingRate = socialDistancingRate;
        this.physicalBarriersRate = physicalBarriersRate;
        this.description = description;
        Date curDate = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.submitDate = dformat.format(curDate).toString();
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Restaurants").child(restaurantID).child("reviews");
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

    public static Review get(Context context, callbackHelper cb, String reviewID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewTable.REVIEW_TABLE);
        selectedValues = null;
        dbRef.child(reviewID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Review.class);
                cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                Log.w(TAG, "onSuccess: Review retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Review retrival failed!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<Review> getAll(Context context, callbackHelper cb, String restaurantID){ // Review for specific restaurant
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Restaurants/" + restaurantID + "/reviews");
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
                cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                Log.w(TAG, "onSuccess: All Review retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Review retrival failed!");
            }
        });
        return reviewList;
    }

//    public static ArrayList<Review> getAllForAUser(Context context, callbackHelper cb, String userID){ // Review for specific restaurant
//        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewTable.REVIEW_TABLE);
//        ArrayList<Review> reviewList = new ArrayList<>();
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot eachData : snapshot.getChildren()){ // setiap child dari snapshot json
//                    if(eachData.child("userID").getValue().equals(userID)) {
//                        Review curReview = eachData.getValue(Review.class);
//                        reviewList.add(curReview);
//                    }
//                }
//                cb.onUserLoadCallback(context, VariablesUsed.currentUser);
//                Log.w(TAG, "onSuccess: All Review retrieved!");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "onFailure: All Review retrival failed!");
//            }
//        });
//        return reviewList;
//    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getReviewID() {
        return reviewID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Double getMaskRate() {
        return maskRate;
    }

    public Double getTemperatureRate() {
        return temperatureRate;
    }

    public Double getSanitizeRate() {
        return sanitizeRate;
    }

    public Double getSocialDistancingRate() {
        return socialDistancingRate;
    }

    public Double getPhysicalBarriersRate() {
        return physicalBarriersRate;
    }

    public String getDescription() {
        return description;
    }

    public static Review getSelectedValues() {
        return selectedValues;
    }

    public String getSubmitDate() {
        return submitDate;
    }
}
