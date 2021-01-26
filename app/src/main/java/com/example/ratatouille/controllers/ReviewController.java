package com.example.ratatouille.controllers;

import android.content.Context;

import com.example.ratatouille.models.Review;
import com.example.ratatouille.models.ReviewVerification;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.viewReservationListFragment;

import java.util.ArrayList;
import java.util.UUID;

public class ReviewController {

    public static Review getAReview(Context context, callbackHelper cb, String reviewID) {
        Review selectedReview = Review.get(context, cb, reviewID);
        return selectedReview;
    }

    public static ArrayList<Review> getAllReviewForARestaurant(Context context, callbackHelper cb, String restaurantID){
        ArrayList<Review> reviewList = Review.getAll(context, cb, restaurantID);
        return reviewList;
    }

    public static ArrayList<Review> getAllReviewForAUser(Context context, callbackHelper cb, String userID){
//        ArrayList<Review> reviewList = Review.getAllForAUser(context, cb, userID);
//        return reviewList;
        return null;
    }

    public static Review addReview(String restaurantID, Double maskRate, Double temperatureRate, Double sanitizeRate, Double socialDistancingRate, Double physicalBarriersRate, String description){
        Review newReview = new Review(UUID.randomUUID().toString(), restaurantID, VariablesUsed.loggedUser.getUid().toString(), VariablesUsed.currentUser.getUsername(),
                maskRate, temperatureRate, sanitizeRate, socialDistancingRate, physicalBarriersRate, description);
        newReview.save();
        return newReview;
    }

    public static void refreshReviewVerificationDatabase() {
        ReviewVerification.refreshDatabase();
    }

    public static void deleteVerificationCode(String reviewCode){
        ReviewVerification.delete(reviewCode);
    }

    // Developers may call this from another class..
    public static void checkReviewVerification(Context context, callbackHelper cb, String reviewCode, String restaurantID){
        ReviewVerification verifier = ReviewVerification.get(context, cb, reviewCode, restaurantID);
    }

    // Only available for the callback feature, for calls by database!
    public static Boolean databaseVerificationCheck(ReviewVerification verifier, String reviewCode, String restaurantID){
        if(verifier == null){
            return false;
        }
        if(!verifier.getReviewCode().toString().equals(reviewCode)){
            return false;
        }
        if(!VariablesUsed.loggedUser.getUid().toString().equals(verifier.getCustomerID())){
            return false;
        }
        if(!verifier.getRestaurantID().equals(restaurantID)){
            return false;
        }
        return true;
    }
}
