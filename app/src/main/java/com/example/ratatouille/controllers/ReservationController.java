package com.example.ratatouille.controllers;

import android.content.Context;

import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.utils.restaurantCB;

import java.util.ArrayList;

public class ReservationController {

    public static ReservationRequest getAReservation(Context context, restaurantCB cb, String reservationID) {
        ReservationRequest reservationData = ReservationRequest.get(context, cb, reservationID);
        return reservationData;
    }

    public static ArrayList<ReservationRequest> getAllReservations(Context context, restaurantCB cb, String restaurantID) {
        ArrayList<ReservationRequest> reservationData = ReservationRequest.getAll(context, cb, restaurantID);
        return reservationData;
    }

    public static void acceptARequest(ReservationRequest reservation){
        reservation.acceptRequest();
    }
}
