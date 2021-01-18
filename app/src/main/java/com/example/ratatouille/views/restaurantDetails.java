package com.example.ratatouille.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.utils.detailPhotoAdapter;
import com.example.ratatouille.utils.photoCallbackHelper;
import com.example.ratatouille.utils.requestMaker;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class restaurantDetails extends AppCompatActivity {

    private PagerAdapter photoAdapter;
    private ViewPager photoView;
    private TextView title, type, timings, address, avg, menu;
    private RatingBar ratingBar;
    private ArrayList<detailPhotoModels> photoList;
    private String id;
    private Context context;

    private photoCallbackHelper cbh = new photoCallbackHelper() {
        @Override
        public void onLoad(PagerAdapter pager) {
            photoView.removeAllViews();
            photoView.setAdapter(pager);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        title = findViewById(R.id.resto_title);
        type = findViewById(R.id.resto_type);
        timings = findViewById(R.id.resto_timing);
        address = findViewById(R.id.resto_address);
        avg = findViewById(R.id.resto_avg_price);
        menu = findViewById(R.id.resto_menu);
        ratingBar = findViewById(R.id.ratingBarRestoDetails);
        photoView = findViewById(R.id.detailPhoto_viewpager);
        context = this;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            id = bundle.getString("id");
        }

        query();
    }

    private void query() {
        String qParam = String.format("restaurant?res_id=" + id);
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
        String location = resto.getJSONObject("location").getString("locality");
        String resto_type = resto.getString("cuisines");
        double rating = resto.getJSONObject("user_rating").getDouble("aggregate_rating");
        String jam_buka = resto.getString("timings");
        String average_price = resto.getString("average_cost_for_two");
        String menu_url = resto.getString("menu_url");

        title.setText(resto_name);
        type.setText(resto_type);
        address.setText(location);
        timings.setText(jam_buka);
        avg.setText(average_price);
        menu.setText(menu_url);
        ratingBar.setRating((float) rating);
        load_photo(resto_id);
    }

    private void load_photo(String id) {
        StorageReference images = DatabaseHelper.getStorage().getReference()
                .child("restaurants/" + id + "/photos");

        images.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        photoList = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            final long ONE_MEGABYTE = 1024 * 1024;
                            item.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    photoList.add(new detailPhotoModels(bitmap));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    System.out.println(exception);
                                }
                            });
                        }
                        photoAdapter = new detailPhotoAdapter(context, photoList);
//                        photoView.setAdapter(photoAdapter);

                        cbh.onLoad(photoAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
    }
}