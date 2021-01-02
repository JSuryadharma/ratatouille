package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Menu {
    private String menuID;
    private String restaurantID;
    private String name;
    private Integer price;

    private static Menu selectedValues = null;

    public Menu() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Menu(String menuID, String restaurantID, String name, Integer price) {
        this.menuID = menuID;
        this.restaurantID = restaurantID;
        this.name = name;
        this.price = price;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.MenuTable.MENU_TABLE);
        dbRef.child(menuID).setValue(this);
    }

    public static void delete(String menuID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.MenuTable.MENU_TABLE);
        dbRef.child(menuID).removeValue();
    }

    public static Menu get(String menuID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.MenuTable.MENU_TABLE);
        selectedValues = null;
        dbRef.child(menuID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Menu.class);
                Log.w(TAG, "onSuccess: Menu retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Menu retrieval failed!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<Menu> getAll(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.MenuTable.MENU_TABLE);
        ArrayList<Menu> menuList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()) {
                    Menu curMenu = snapshot.getValue(Menu.class);
                    menuList.add(curMenu);
                }
                Log.w(TAG, "onSuccess: All Menu retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Menu retrieval failed!");
            }
        });
        return menuList;
    }

    public static ArrayList<Menu> getForRestaurant(String restaurantID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.MenuTable.MENU_TABLE);
        ArrayList<Menu> menuList = new ArrayList<>();
        dbRef.child(restaurantID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()) {
                    Menu curMenu = snapshot.getValue(Menu.class);
                    menuList.add(curMenu);
                }
                Log.w(TAG, "onSuccess: All Menu (Specified for Restaurant) retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Menu (Specified for Restaurant) retrieval failed!");
            }
        });
        return menuList;
    }
}
