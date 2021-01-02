package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Types {
    private String typeID;
    private String typeName;

    private static Types selectedValues = null;

    public Types() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Types(String typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.TypesTable.TYPES_TABLE);
        dbRef.child(typeID).setValue(this);
    }

    public static void delete(String typeID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.TypesTable.TYPES_TABLE);
        dbRef.child(typeID).removeValue();
    }

    public static Types get(String typeID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.TypesTable.TYPES_TABLE);

        selectedValues = null;

        dbRef.child(typeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Types.class);
                Log.w(TAG, "onSuccess: Types retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Types failed to be retrieved!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<Types> getAll(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.TypesTable.TYPES_TABLE);

        ArrayList<Types> typeList = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){
                    Types curTypes = eachData.getValue(Types.class);
                    typeList.add(curTypes);
                }
                Log.w(TAG, "onSuccess: All Types retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Types failed to be retrieved!");
            }
        });
        return typeList;
    }

    public String getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }
}
