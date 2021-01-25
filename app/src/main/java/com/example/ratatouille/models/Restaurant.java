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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Restaurant {
    private String restaurantID;
    private String name, description, phone, address;
    private String email, password;

    private static Restaurant selectedValues = null;

    public Restaurant() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Restaurant(String restaurantID, String name, String description, String phone, String address, String email, String password) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTable.RESTAURANT_TABLE);
        dbRef.child(VariablesUsed.loggedUser.getUid()).setValue(this);
    }

    public static void delete(String restaurantID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTable.RESTAURANT_TABLE);
        dbRef.child(restaurantID).removeValue();
    }

    public Restaurant update(String name, String description, String phone, String address) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.address = address;

        save();

        return this;
    }

    public static Restaurant get(String restaurantID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTable.RESTAURANT_TABLE);

        selectedValues = null;

        dbRef.child(restaurantID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Restaurant.class);
                Log.w(TAG, "onSuccess: Restaurant Data Successfully Retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Restaurant Data Failed to be Retrieved!");

            }
        });
        return selectedValues;
    }

    public static ArrayList<Restaurant> getAll(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTable.RESTAURANT_TABLE);

        ArrayList<Restaurant> restaurantList = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant curRestaurant = snapshot.getValue(Restaurant.class);
                restaurantList.add(curRestaurant);
                Log.w(TAG, "onSuccess: All Restaurant Data Successfully Retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Restaurant Data Failed to be Retrieved!");
            }
        });

        return restaurantList;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public static Restaurant getSelectedValues() {
        return selectedValues;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
