package com.example.ratatouille.controllers;

import android.content.Context;

import com.example.ratatouille.models.Favourite;
import com.example.ratatouille.utils.callbackHelper;

import java.util.ArrayList;

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
