package com.example.ratatouille.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.Favourite;
import com.example.ratatouille.models.Review;
import com.example.ratatouille.models.ReviewVerification;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.AddReviewCodeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class FavouritesController {
    public static ArrayList<Favourite> getAllFavouritesForAUser(Context context, callbackHelper cb, String userID){
        ArrayList<Favourite> favouriteList = Favourite.getAll(context, cb, userID);
        return favouriteList;
    }

    public static Favourite addFavourite(Context context, String name, String types, String prices, Double rating, String favouriteID, String userID, String photoUrl){
        Favourite newFavourite = new Favourite(name, types, prices, rating, favouriteID, userID, photoUrl);
        newFavourite.save(context);
        return newFavourite;
    }
}
