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

public class HelpTicket {
    private String ticketID;
    private String customerID;
    private String problemSubject, description, screenshot, askHelpDate;
    private Boolean isSolved;
    private static HelpTicket selectedValues = null;

    public HelpTicket() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HelpTicket(String ticketID, String customerID, String problemSubject, String description, String screenshot, String askHelpDate, Boolean isSolved) {
        this.ticketID = ticketID;
        this.customerID = customerID;
        this.problemSubject = problemSubject;
        this.description = description;
        this.screenshot = screenshot;
        this.askHelpDate = askHelpDate;
        this.isSolved = isSolved;
    }

    public void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketTable.HELPTICKET_TABLE);

        dbRef.child(ticketID).setValue(this);
    }

    public static void delete(String ticketID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketTable.HELPTICKET_TABLE);
        dbRef.child(ticketID).removeValue();
    }

    public static HelpTicket get(String ticketID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketTable.HELPTICKET_TABLE);

        selectedValues = null;

        dbRef.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(HelpTicket.class);
                Log.w(TAG, "onSuccess: HelpTicket successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: HelpTicket failed to be retrieved!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<HelpTicket> getAll(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.HelpTicketTable.HELPTICKET_TABLE);

        ArrayList<HelpTicket> ticketList = new ArrayList<HelpTicket>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){
                    HelpTicket curTicket = eachData.getValue(HelpTicket.class);
                    ticketList.add(curTicket);
                }
                Log.w(TAG, "onSuccess: All HelpTicket successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All HelpTicket failed to be retrieved!");
            }
        });
        return ticketList;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getProblemSubject() {
        return problemSubject;
    }

    public String getDescription() {
        return description;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public String getAskHelpDate() {
        return askHelpDate;
    }

    public Boolean getIsSolved() {
        return isSolved;
    }
}
