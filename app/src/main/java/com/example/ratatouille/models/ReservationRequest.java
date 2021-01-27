package com.example.ratatouille.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.restaurantCB;
import com.example.ratatouille.vars.VariablesUsed;
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
    private Integer numberOfPerson;
    private String description;
    private String reply;
    private Boolean isAccepted;

    private static ReservationRequest selectedValues = null;

    public ReservationRequest(){
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ReservationRequest(String reservationRequestID, String userID, String restaurantID,
                              String requestDate, String reserveDate, Integer numberOfPerson,
                              String description) {
        this.reservationRequestID = reservationRequestID;
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.requestDate = requestDate;
        this.reserveDate = reserveDate;
        this.numberOfPerson = numberOfPerson;
        this.reply = null;
        this.description = description;
        this.isAccepted = null;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        dbRef.child(reservationRequestID).setValue(this);
    }

    public void delete(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        dbRef.child(this.reservationRequestID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
                Log.w(TAG, "onSuccess: Reservation deletion completed!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Reservation deletion failed!");
            }
        });
    }

    public void replyReservation(String message){
        if(message != null){
            this.reply = message;
        }
        this.save();
    }

    public ReservationRequest update(String requestDate, String reserveDate, Integer numberOfPerson, String description, Boolean isAccepted){
        this.requestDate = requestDate;
        this.reserveDate = reserveDate;
        this.numberOfPerson = numberOfPerson;
        this.description = description;
        this.isAccepted = isAccepted;
        save();

        return this;
    }

    public void acceptRequest(){
        this.isAccepted = true;
        save();
    }

    public static ReservationRequest get(Context context, restaurantCB cb, String reservationRequestID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        selectedValues = null;
        dbRef.child(reservationRequestID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(ReservationRequest.class);
                cb.onRestaurantCB(context, VariablesUsed.currentRestaurant);
                Log.w(TAG, "onSuccess: ReservationRequest retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: ReservationRequest retrieval failed!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<ReservationRequest> getAll(Context context, restaurantCB cb, String restaurantID, String userID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.ReservationRequestTable.RESERVATIONREQUEST_TABLE);
        ArrayList<ReservationRequest> reservationRequestList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData :snapshot.getChildren()){
                    if(eachData.child("restaurantID").getValue().equals(restaurantID) && userID == null){
                        System.out.println("restoran");
                        ReservationRequest curRequest = eachData.getValue(ReservationRequest.class);
                        reservationRequestList.add(curRequest);
                        cb.onRestaurantCB(context, VariablesUsed.currentRestaurant);
                        break;
                    }
                    if(eachData.child("userID").getValue().equals(userID) && restaurantID == null){
                        System.out.println("user");
                        ReservationRequest curRequest = eachData.getValue(ReservationRequest.class);
                        reservationRequestList.add(curRequest);
                        cb.onRestaurantCB(null, null);
                    }
                }
                Log.w(TAG, "onSuccess: All ReservationRequest retrieved successfully!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All ReservationRequest retrieval failed!");
            }
        });
        return reservationRequestList;
    }

    public String getReservationRequestID() {
        return reservationRequestID;
    }

    public String getUserID() {
        return userID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public Integer getNumberOfPerson() {
        return numberOfPerson;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setReservationRequestID(String reservationRequestID) {
        this.reservationRequestID = reservationRequestID;
    }

    public String getReply() {
        return reply;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ReservationRequest getSelectedValues() {
        return selectedValues;
    }

    public static void setSelectedValues(ReservationRequest selectedValues) {
        ReservationRequest.selectedValues = selectedValues;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public void setNumberOfPerson(Integer numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
