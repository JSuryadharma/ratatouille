package com.example.ratatouille.models;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class ReviewVerification {
    private String reviewCode;
    private String restaurantID;
    private String customerID;
    private Date submitDate;
    private static ReviewVerification selectedValues;

    public ReviewVerification(){
        //blank constructor, required..
    }

    public ReviewVerification(String reviewCode, String restaurantID, String customerID) {
        this.reviewCode = reviewCode;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        Date date = new Date();
        this.submitDate = date;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewVerification.REVIEWVERIFICATION_TABLE);
        dbRef.child(reviewCode).setValue(this);
    }

    public static void delete(String reviewCode){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewVerification.REVIEWVERIFICATION_TABLE);
        dbRef.child(reviewCode).removeValue();
    }

    public static ReviewVerification get(Context context, callbackHelper cb, String reviewCode, String restaurantID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewVerification.REVIEWVERIFICATION_TABLE);

        selectedValues = null;

        dbRef.child(reviewCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(ReviewVerification.class);
                if(ReviewController.databaseVerificationCheck(selectedValues, reviewCode, restaurantID)){
//                    Utils.showDialogMessage(R.drawable.verified_logo, context, "Successfully Verified", "Please kindly rate your selected restaurant.");
                    ReviewController.deleteVerificationCode(reviewCode);
                    cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Invalid Indentification", "Your inputted code doesn't match your selected restaurant, or your account id!");
                }
                Log.w(TAG, "onSuccess: Review Verification Retrieval Successful!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Review Verification Retrieval Failed!");
            }
        });
        return selectedValues;
    }

    public static void refreshDatabase(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReviewVerification.REVIEWVERIFICATION_TABLE);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    ReviewVerification currentData = ds.getValue(ReviewVerification.class);
                    if(!DateUtils.isToday(currentData.getSubmitDate().getTime())){
                        ReviewVerification.delete(currentData.getReviewCode());
                    }
                }
                Log.w(TAG, "onSuccess: Refreshing Review Verification Datas Successful!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Refreshing Review Verification Datas Failed!");
            }
        });
    }

    public String getReviewCode() {
        return reviewCode;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setReviewCode(String reviewCode) {
        this.reviewCode = reviewCode;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
