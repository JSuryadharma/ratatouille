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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userReservationAdapter extends RecyclerView.Adapter<userReservationAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ReservationRequest> requestList;
    private String restoName;

    public userReservationAdapter(Context context, ArrayList<ReservationRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public userReservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_userreservation, parent, false);
        return new userReservationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userReservationAdapter.MyViewHolder holder, int position) {
        ReservationRequest currentRequest = requestList.get(position);

        holder.reservationID.setText(currentRequest.getRestaurantID());

        loadRestoData(holder, currentRequest.getRestaurantID());

        holder.reservationDate.setText(currentRequest.getReserveDate().toString());
        holder.reservationSeats.setText(currentRequest.getNumberOfPerson().toString() + " Person(s)");
        holder.reservationDesc.setText(currentRequest.getDescription());

        String temp;
        if(currentRequest.getAccepted() == null) {
            temp = "Pending";
        }
        else if(currentRequest.getAccepted() == true) {
            temp = "Accepted";
        }
        else {
            temp = "Rejected";
        }
        holder.reservationStatus.setText(temp);
        System.out.println("Sampe sini");
    }

    private void loadRestoData(MyViewHolder holder, String restoID) {
        userReservationCallback ch = new userReservationCallback() {
            @Override
            public void onLoadCallback(String name) {
                holder.restaurantName.setText(name);
            }
        };
        String qParam = String.format("restaurant?res_id=" + restoID);
        String url = String.format(VariablesUsed.API_BASE_URL + qParam);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    getValue(response, ch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", VariablesUsed.API_KEY);
                return headers;
            }
        };

        // Access the RequestQueue through your requestMaker
        requestMaker.getInstance(context).addToRequestQueue(req);
    }

    public void getValue(JSONObject object, userReservationCallback ch) throws JSONException {
        JSONObject resto = object;

        String resto_name = resto.getString("name");
        restoName = resto_name;
        ch.onLoadCallback(resto_name);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout reservationArea;
        private TextView reservationID;
        private TextView restaurantName;
        private TextView reservationSeats;
        private TextView reservationDate;
        private TextView reservationDesc;
        private TextView reservationStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationArea = itemView.findViewById(R.id.reservationsrv_area);
            reservationID = itemView.findViewById(R.id.userReservationReservationID);
            restaurantName = itemView.findViewById(R.id.userReservationRestoName);
            reservationSeats = itemView.findViewById(R.id.userReservationSeat);
            reservationDate = itemView.findViewById(R.id.userReservationDate);
            reservationDesc = itemView.findViewById(R.id.userReservationDesc);
            reservationStatus = itemView.findViewById(R.id.userReservationStatus);
        }
    }
}
