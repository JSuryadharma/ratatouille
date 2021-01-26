package com.example.ratatouille.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class reservationRecyclerAdapter extends RecyclerView.Adapter<reservationRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ReservationRequest> requestList;
    private Users currentBooker;
    private restaurantReservationCallbackHelper cb = new restaurantReservationCallbackHelper() {
        @Override
        public void onLoadCallback(MyViewHolder holder, Users u) {
            setReservationUsername(holder, u);
        }
    };
    private Utils.reservationResponse response = new Utils.reservationResponse() {
        @Override
        public void accResponse(ReservationRequest curRequest) {
            Utils.showDialogMessage(R.drawable.verified_logo, context, "Acknowledge", "Order accepted!");
            MediaPlayer player = MediaPlayer.create(context, R.raw.ding);
            player.start();
            curRequest.acceptRequest();
        }

        @Override
        public void decResponse(ReservationRequest curRequest) {
            Utils.showDialogMessage(R.drawable.ic_warning, context, "Action Confirmed", "Order Rejected!");
            MediaPlayer player = MediaPlayer.create(context, R.raw.ding);
            player.start();
            player.start();
            player.start();
            curRequest.delete();
        }
    };

    public reservationRecyclerAdapter(Context context, ArrayList<ReservationRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_reservationlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReservationRequest currentRequest = requestList.get(position);

        currentBooker = null;

        holder.reservationArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showReservationOptDialog(context, response, currentRequest, "Action Selection for:\n" + currentRequest.getReserveDate() + "\n for " + currentRequest.getNumberOfPerson() + " seats.");
            }
        });

        holder.reservationID.setText(currentRequest.getRestaurantID());
        holder.reservationName.setText("loading..");

        currentBooker = UserController.getAUserForReservation(cb, holder, currentRequest.getUserID());

        holder.reservationDate.setText(currentRequest.getReserveDate().toString());
        holder.reservationSeats.setText(currentRequest.getNumberOfPerson().toString());
        holder.reservationDesc.setText(currentRequest.getDescription());
    }

    public void setReservationUsername(MyViewHolder holder, Users user) {
        holder.reservationName.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout reservationArea;
        private TextView reservationID;
        private TextView reservationName;
        private TextView reservationSeats;
        private TextView reservationDate;
        private TextView reservationDesc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationArea = itemView.findViewById(R.id.reservationsrv_area);
            reservationID = itemView.findViewById(R.id.reservationsrv_id);
            reservationName = itemView.findViewById(R.id.reservationsrv_name);
            reservationSeats = itemView.findViewById(R.id.reservationsrv_seats);
            reservationDate = itemView.findViewById(R.id.reservationsrv_date);
            reservationDesc = itemView.findViewById(R.id.reservationsrv_desc);
        }
    }
}
