package com.example.ratatouille.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.R;
import com.example.ratatouille.models.mostPopularModels;
import com.example.ratatouille.utils.mostPopularAdapter;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.vars.VariablesUsed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class Search extends AppCompatActivity {

    RecyclerView searched_recyclerview;
    com.example.ratatouille.utils.mostPopularAdapter mostPopularAdapter;
    TextView searchedBox;
    Context context;
    LinearLayout backButton;
    TextView backButton_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        customType(Search.this, "fadein-to-fadeout");
        context = this;

        String searched = null;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            searched = bundle.getString("q");
        }

        searched_recyclerview = findViewById(R.id.searched_recyclerview);
        searched_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchedBox = findViewById(R.id.searched_box);
        searchedBox.setText(searched);
        backButton = findViewById(R.id.search_backButton);
        backButton_text = findViewById(R.id.search_backButton_text);

        searchedBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Search(context, searchedBox.getText().toString());
                }
            }
        });

        backButton_text.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                Fragment backFragment = new ViewProfileFragment();
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                backToMainMenu(context);
            }
        });

        Search(context, searched);
    }

    public void Search(Context context, String text) {
        String qParam = String.format("search?q=" + text);
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
        JSONArray restaurants = null;
        ArrayList<mostPopularModels> mostPopularList = new ArrayList<>();
        try {
            restaurants = object.getJSONArray("restaurants");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<restaurants.length(); i++) {
            JSONObject resto = null;
            resto = restaurants.getJSONObject(i).getJSONObject("restaurant");

            String resto_name = resto.getString("name");
            String location = resto.getJSONObject("location").getString("locality");
            String type = resto.getString("cuisines");
            double rating = resto.getJSONObject("user_rating").getDouble("aggregate_rating");
            String photo_url = resto.getString("thumb");
            String jam_buka = resto.getString("timings");

            mostPopularList.add(new mostPopularModels(resto_name + " - " + location,
                    type, (float) rating, jam_buka, photo_url));
        }

        mostPopularAdapter = new mostPopularAdapter(this, mostPopularList);
        searched_recyclerview.setAdapter(mostPopularAdapter);
    }

    public void backToMainMenu(Context context) {
        Intent mainMenuIntent = new Intent(context, customerView.class);
        startActivity(mainMenuIntent);
        finish();
    }
}