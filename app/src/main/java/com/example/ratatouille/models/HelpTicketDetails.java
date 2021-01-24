package com.example.ratatouille.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

// assumed that Details CANNOT BE UPDATED!

public class HelpTicketDetails {
    private String messageID;
    private String ticketID;
    private String userID;
    private String userName;
    private String message;
    private String submitDate;

    private static HelpTicketDetails selectedValues = null;

    public HelpTicketDetails() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HelpTicketDetails(String messageID, String ticketID, String userID, String userName, String message) {
        this.messageID = messageID;
        this.ticketID = ticketID;
        this.userID = userID;
        this.userName = userName;
        this.message = message;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.submitDate = df.format(date);
    }

    public void save() {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketDetailsTable.HELPTICKETDETAILS_TABLE);

        dbRef.child(messageID).setValue(this);
    }

    public static void delete(String messageID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketDetailsTable.HELPTICKETDETAILS_TABLE);
        dbRef.child(messageID).removeValue();
    }

    public static HelpTicketDetails get(String messageID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketDetailsTable.HELPTICKETDETAILS_TABLE);

        selectedValues = null;

        dbRef.child(messageID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(HelpTicketDetails.class);
                Log.w(TAG, "onSuccess: HelpTicketDetails successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All HelpTicketDetails failed to be retrieved!");
            }
        });

        return selectedValues;
    }

    public static ArrayList<HelpTicketDetails> getAll(Context context, callbackHelper cb, String ticketID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketDetailsTable.HELPTICKETDETAILS_TABLE);

        ArrayList<HelpTicketDetails> detailList = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){
                    if(eachData.child("ticketID").getValue().toString().equals(ticketID)) {
                        HelpTicketDetails curDetail = eachData.getValue(HelpTicketDetails.class);
                        detailList.add(curDetail);
                    }
                }
                Log.w(TAG, "onSuccess: All HelpTicketDetails successfully retrieved!");
                cb.onUserLoadCallback(context, VariablesUsed.currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All HelpTicketDetails failed to be retrieved!");
            }
        });

        return detailList;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getSubmitDate() {
        return submitDate;
    }
}
