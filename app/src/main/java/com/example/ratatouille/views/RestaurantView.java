package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReservationController;
import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.mostPopularModels;
import com.example.ratatouille.utils.mostPopularAdapter;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.utils.reservationRecyclerAdapter;
import com.example.ratatouille.utils.restaurantCB;
import com.example.ratatouille.vars.VariablesUsed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantView extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView restaurantTitle;
    private RecyclerView reservation_RecyclerView;
    private Context context;

    private restaurantCB cb = new restaurantCB() {
        @Override
        public void onRestaurantCB(Context context, Restaurant restaurant) {
            setReservationList();
        }
    };

    private ArrayList<ReservationRequest> reservationData;
    public RestaurantView() {
        // empty constructor.. required
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        refreshLayout = findViewById(R.id.restaurant_refresh);
        backButton = findViewById(R.id.restaurant_backButton);
        backButton_text = findViewById(R.id.restaurant_backButton_text);
        restaurantTitle = findViewById(R.id.restaurant_name);
        reservation_RecyclerView = findViewById(R.id.restaurant_recyclerView);
        context = this;
        reservationData = new ArrayList<>();

        loadRestoData(context);

        backButton_text.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(RestaurantView.this, R.raw.personleave);
                player.start();
                Intent backIntent = new Intent(RestaurantView.this, loginScreen.class);
                startActivity(backIntent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                refreshLayout.setRefreshing(false);
            }
        });

        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                reload();
            }
        };
        handler.postDelayed(run, 4000); // every 4 seconds need to refresh, almost realtime..
    }

    private void reload() {
        reservationData = ReservationController.getAllReservations(RestaurantView.this, cb, VariablesUsed.currentRestaurant.getRestaurantID(), null);
    }

    private void setReservationList() {
        reservationRecyclerAdapter reservation_RecyclerAdapter = new reservationRecyclerAdapter(RestaurantView.this, reservationData);
        reservation_RecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantView.this));
        reservation_RecyclerView.setAdapter(reservation_RecyclerAdapter);
    }

    private void loadRestoData(Context context) {
        String qParam = String.format("restaurant?res_id=" + VariablesUsed.currentRestaurant.getRestaurantID());
        String url = String.format(VariablesUsed.API_BASE_URL + qParam);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    getValue(response);
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

    public void getValue(JSONObject object) throws JSONException {
        JSONObject resto = object;

        String resto_id = resto.getString("id");
        String resto_name = resto.getString("name");
        String address = resto.getJSONObject("location").getString("address");
        String locality = resto.getJSONObject("location").getString("locality");
        String type = resto.getString("cuisines");
        String phone = resto.getString("phone_numbers");

        VariablesUsed.currentRestaurant.setRestaurantID(resto_id);
        VariablesUsed.currentRestaurant.setDescription(type);
        VariablesUsed.currentRestaurant.setAddress(address);
        VariablesUsed.currentRestaurant.setPhone(phone);
        VariablesUsed.currentRestaurant.setName(resto_name);
        restaurantTitle.setText(VariablesUsed.currentRestaurant.getName() + " - " + locality);
    }
}
