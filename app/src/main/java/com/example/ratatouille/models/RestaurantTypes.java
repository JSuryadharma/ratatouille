package com.example.ratatouille.models;

// assumed that Details CANNOT BE UPDATED!

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

public class RestaurantTypes {
    private static Types selectedValues = null;

    public void save(Restaurant restaurant, Types types){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTypes.RESTAURANTTYPES_TABLE).child(VariablesUsed.loggedUser.getUid());
        dbRef.child(types.getTypeID()).setValue(types);
    }

    public static void delete(String userID, String ticketID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTypes.RESTAURANTTYPES_TABLE);
        dbRef.child(userID).child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    snapshot.getRef().removeValue();
                    Log.w(TAG, "onSuccess: RestaurantTypes Data deleted!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: RestaurantTypes Data failed to be deleted!");
            }
        });
    }

    public ArrayList<Types> getTypesForARestaurant(String userID){ // assumed that Restaurant is also a User..
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.RestaurantTypes.RESTAURANTTYPES_TABLE).child(userID);

        ArrayList<Types> typeList = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){
                    Types curTypes = eachData.getValue(Types.class);
                    typeList.add(curTypes);
                }
                Log.w(TAG, "onSuccess: RestaurantTypes retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: RestaurantTypes failed to retrieve!");
            }
        });
        return typeList;
    }
}
