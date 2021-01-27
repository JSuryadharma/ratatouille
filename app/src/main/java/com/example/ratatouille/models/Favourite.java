package com.example.ratatouille.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Favourite {

    private String name, type, price, favouriteID, userID, photoUrl;
    private Double rating;

    // Empty constructor
    public Favourite() { }

    public Favourite(String name, String type, String price, Double rating, String favouriteID, String userID, String photoUrl) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.rating = rating;
        this.favouriteID = favouriteID;
        this.userID = userID;
        this.photoUrl = photoUrl;
    }

    public void save(Context context){
        Boolean flag = false;
        callbackHelper cb = new callbackHelper() {
            @Override
            public void onUserLoadCallback(Context context, Users u) {

            }
        };

        ArrayList<Favourite> listAllFav = getAll(null, cb, userID);
        for (Favourite a : listAllFav) {
            if(a.getFavouriteID().equals(favouriteID)) {
                flag = true;
                break;
            }
        }

        if(flag == false) {
            DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Favourites").child(favouriteID);
            dbRef.setValue(this);
            Utils.showDialogMessage(R.drawable.verified_logo, context, "Sucess", "Success adding!");
            return;
        }
        Utils.showDialogMessage(R.drawable.ic_warning, context, "Failed", "Failed Add to Favourites");
    }

    public static ArrayList<Favourite> getAll(Context context, callbackHelper cb, String userID){ // Review for specific restaurant
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Favourites");
        ArrayList<Favourite> favouriteList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){ // setiap child dari snapshot json
                    Favourite curReview = eachData.getValue(Favourite.class);
                    if(curReview.getUserID().equals(userID)) favouriteList.add(curReview);
                }
                cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                Log.w(TAG, "onSuccess: All Review retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Review retrival failed!");
            }
        });
        return favouriteList;
    }

    public static void delete(String userID, String favouriteID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference("Users").child(userID).child("favourites");
        dbRef.child(favouriteID).removeValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFavouriteID() {
        return favouriteID;
    }

    public void setFavouriteID(String favouriteID) {
        this.favouriteID = favouriteID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
