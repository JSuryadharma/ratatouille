package com.example.ratatouille.controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.RestaurantView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RestaurantController {

    public static void RestaurantLogin(Context context, String email, String password){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTable.RESTAURANT_TABLE);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    System.out.println(ds.child("restaurantID").getValue());
                    if(ds.child("restaurantID").getValue() == null) continue;
                    Restaurant curValue = new Restaurant(ds.child("restaurantID").getValue(Long.class).toString(), null, null, null, null, ds.child("email").getValue(String.class), ds.child("password").getValue(String.class));
                    if(curValue.getEmail().equals(email) && curValue.getPassword().equals(password)){
                        VariablesUsed.currentRestaurant = curValue;
                        Intent homeIntent = new Intent(context, RestaurantView.class);
                        context.startActivity(homeIntent);
                        return;
                    }
                }
                Utils.showDialogMessage(R.drawable.ic_warning, context, "Failed to Login", "Sorry, you got wrong email / password input.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
