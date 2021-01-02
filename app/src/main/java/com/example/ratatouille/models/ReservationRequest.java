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

public class ReservationRequest {
    private String reservationRequestID;
    private String userID;
    private String restaurantID;
    private String requestDate;
    private String reserveDate;
    private String reserveTime;
    private Integer numberOfPerson;
    private Boolean isAccepted;

    private static ReservationRequest selectedValues = null;

    public ReservationRequest(){
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ReservationRequest(String reservationRequestID, String userID, String restaurantID, String requestDate, String reserveDate, String reserveTime, Integer numberOfPerson, Boolean isAccepted) {
        this.reservationRequestID = reservationRequestID;
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.requestDate = requestDate;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.numberOfPerson = numberOfPerson;
        this.isAccepted = isAccepted;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        dbRef.child(reservationRequestID).setValue(this);
    }

    public ReservationRequest update(String requestDate, String reserveDate, String reserveTime, Integer numberOfPerson, Boolean isAccepted){
        this.requestDate = requestDate;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.numberOfPerson = numberOfPerson;
        this.isAccepted = isAccepted;

        save();

        return this;
    }

    public static ReservationRequest get(String reservationRequestID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        selectedValues = null;
        dbRef.child(reservationRequestID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(ReservationRequest.class);
                Log.w(TAG, "onSuccess: ReservationRequest retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: ReservationRequest retrieval failed!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<ReservationRequest> getAll(String userID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        ArrayList<ReservationRequest> reservationRequestList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData :snapshot.getChildren()){
                    if(eachData.child("userID").getValue().equals(userID)){
                        ReservationRequest curRequest = eachData.getValue(ReservationRequest.class);
                        reservationRequestList.add(curRequest);
                    }
                }
                Log.w(TAG, "onSuccess: All ReservationRequest (Specified to a user) retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All ReservationRequest (Specified to a user) retrieval failed!");
            }
        });
        return reservationRequestList;
    }
}
