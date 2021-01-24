package com.example.ratatouille.controllers;

import android.content.Context;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.models.HelpTicketDetails;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class HelpTicketController {

    public static ArrayList<HelpTicket> getTicketList(Context context, callbackHelper cb, String userID) {
        ArrayList<HelpTicket> userTicketList = HelpTicket.getForAUser(context, cb, userID);
        return userTicketList;
    }

    public static ArrayList<HelpTicketDetails> getTicketDetails(Context context, callbackHelper cb, String ticketID){
        ArrayList<HelpTicketDetails> ticketDetails = HelpTicketDetails.getAll(context, cb, ticketID);
        return ticketDetails;
    }

    public static HelpTicket addTicket(String problemSubject, String description, String screenshot, Date askHelpDate, Boolean isSolved){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        HelpTicket toBeAdded = new HelpTicket(UUID.randomUUID().toString(), DatabaseHelper.getDbAuth().getUid(), problemSubject, description, screenshot, df.format(askHelpDate), isSolved);
        toBeAdded.save();
        return toBeAdded;
    }

    public static HelpTicketDetails addDetails(String ticketID, String userID, String userName, String message) {
        HelpTicketDetails toBeAdded = new HelpTicketDetails(UUID.randomUUID().toString(), ticketID, userID, userName, message);
        toBeAdded.save();
        return toBeAdded;
    }

    public static void closeHelpTicket(HelpTicket helpTicket){
        helpTicket.closeTicket();
    }
}
