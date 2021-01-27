package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Review;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.reviewRecyclerAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class reviewPage extends AppCompatActivity {
    private String currentRestaurantID = "";
    private String currentRestaurantName = "";
    private LinearLayout addButton;
    private SwipeRefreshLayout refresh;
    private TextView restaurantName_overall;
    private TextView overall_score, zomato_score;
    private RatingBar maskRate_overall;
    private RatingBar tempRate_overall;
    private RatingBar sanitizeRate_overall;
    private RatingBar socialDistRate_overall;
    private RatingBar physicalBarRate_overall;
    private RecyclerView review_recyclerView;
    private ArrayList<Review> reviewList;
    private reviewRecyclerAdapter reviewAdapter;
    private ImageView backButton;
    private Context context;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            refreshReviewList();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        addButton = findViewById(R.id.reviewpage_addButton);
        refresh = findViewById(R.id.reviewpage_refresh);
        restaurantName_overall = findViewById(R.id.reviewpage_restaurant_name);
        overall_score = findViewById(R.id.reviewpage_overall_score);
        zomato_score = findViewById(R.id.reviewpage_zomato_score);
        maskRate_overall = findViewById(R.id.reviewpage_maskRatingBar);
        tempRate_overall = findViewById(R.id.reviewpage_tempRatingBar);
        sanitizeRate_overall = findViewById(R.id.reviewpage_sanitationRatingBar);
        socialDistRate_overall = findViewById(R.id.reviewpage_socialDistRatingBar);
        physicalBarRate_overall = findViewById(R.id.reviewpage_physicalBarRatingBar);
        backButton = findViewById(R.id.reviewPageBackButton);
        currentRestaurantID = VariablesUsed.currentRestoDetail.getResto_id();
        context = this;

        review_recyclerView = findViewById(R.id.reviewpage_recyclerView);
        review_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantName_overall.setText(VariablesUsed.currentRestoDetail.getResto_name());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReviewCode.class);
                startActivity(intent);
            }
        });

        reload();
        zomato_score.setText(String.valueOf((float) VariablesUsed.currentRestoDetail.getRating()));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    public void reload(){
        reviewList = ReviewController.getAllReviewForARestaurant(this, cb, currentRestaurantID);
        Collections.sort(reviewList, new Comparator<Review>() {
            @Override
            public int compare(Review review, Review t1) {
                Double ovrall = (review.getMaskRate() + review.getTemperatureRate() + review.getSanitizeRate() + review.getPhysicalBarriersRate() + review.getSocialDistancingRate())/5;
                Double ovrall2 = (t1.getMaskRate() + t1.getTemperatureRate() + t1.getSanitizeRate() + t1.getPhysicalBarriersRate() + t1.getSocialDistancingRate())/5;
                return ovrall > ovrall2 ? 1 : 0;
            }
        });
        currentRestaurantName = VariablesUsed.currentRestoDetail.getResto_id();
    }

    public void refreshReviewList(){
        Collections.sort(reviewList, new Comparator<Review>() {
            @Override
            public int compare(Review review, Review t1) {
                Double ovrall = (review.getMaskRate() + review.getTemperatureRate() + review.getSanitizeRate() + review.getPhysicalBarriersRate() + review.getSocialDistancingRate())/5;
                Double ovrall2 = (t1.getMaskRate() + t1.getTemperatureRate() + t1.getSanitizeRate() + t1.getPhysicalBarriersRate() + t1.getSocialDistancingRate())/5;
                return ovrall > ovrall2 ? 1 : 0;
            }
        });


        reviewAdapter = new reviewRecyclerAdapter(this, reviewList);
        review_recyclerView.setAdapter(reviewAdapter);

        // Refresh the current overall score
        Double maskRateTotal = new Double(0);
        Double tempRateTotal = new Double(0);
        Double sanitizeRateTotal = new Double(0);
        Double socialDistRateTotal = new Double(0);
        Double physicalBarRateTotal = new Double(0);

        for(Review curReview : reviewList){
            maskRateTotal += curReview.getMaskRate();
            tempRateTotal += curReview.getTemperatureRate();
            sanitizeRateTotal += curReview.getSanitizeRate();
            socialDistRateTotal += curReview.getSocialDistancingRate();
            physicalBarRateTotal += curReview.getPhysicalBarriersRate();
        }

        maskRateTotal /= reviewList.size();
        tempRateTotal /= reviewList.size();
        sanitizeRateTotal /= reviewList.size();
        socialDistRateTotal /= reviewList.size();
        physicalBarRateTotal /= reviewList.size();

        maskRate_overall.setRating(maskRateTotal.floatValue());
        tempRate_overall.setRating(tempRateTotal.floatValue());
        sanitizeRate_overall.setRating(sanitizeRateTotal.floatValue());
        socialDistRate_overall.setRating(socialDistRateTotal.floatValue());
        physicalBarRate_overall.setRating(physicalBarRateTotal.floatValue());

        Double overallScore = new Double(0);
        overallScore += maskRateTotal;
        overallScore += tempRateTotal;
        overallScore += sanitizeRateTotal;
        overallScore += socialDistRateTotal;
        overallScore += physicalBarRateTotal;

        overallScore /= 5;

        DecimalFormat df = new DecimalFormat("0.0");

        overall_score.setText(df.format(overallScore).toString());
        // Do not neet callback, should get the values after the reviewList got its values, from the database..
        restaurantName_overall.setText(currentRestaurantName);
    }

}
