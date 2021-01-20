package com.example.ratatouille.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class reviewFragment extends Fragment {
    private String currentRestaurantID = "";
    private String currentRestaurantName = "";
    private LinearLayout addButton;
    private SwipeRefreshLayout refresh;
    private TextView restaurantName_overall;
    private TextView overall_score;
    private RatingBar maskRate_overall;
    private RatingBar tempRate_overall;
    private RatingBar sanitizeRate_overall;
    private RatingBar socialDistRate_overall;
    private RatingBar physicalBarRate_overall;
    private RecyclerView review_recyclerView;
    private ArrayList<Review> reviewList;
    private reviewRecyclerAdapter reviewAdapter;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            refreshReviewList();
        }
    };

    public reviewFragment(String currentRestaurantID) {
        //Set the current RestaurantID, for each view page objects.
        this.currentRestaurantID = currentRestaurantID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = getView().findViewById(R.id.reviewpage_addButton);
        refresh = getView().findViewById(R.id.reviewpage_refresh);
        restaurantName_overall = getView().findViewById(R.id.reviewpage_restaurant_name);
        overall_score = getView().findViewById(R.id.reviewpage_overall_score);
        maskRate_overall = getView().findViewById(R.id.reviewpage_maskRatingBar);
        tempRate_overall = getView().findViewById(R.id.reviewpage_tempRatingBar);
        sanitizeRate_overall = getView().findViewById(R.id.reviewpage_sanitationRatingBar);
        socialDistRate_overall = getView().findViewById(R.id.reviewpage_socialDistRatingBar);
        physicalBarRate_overall = getView().findViewById(R.id.reviewpage_physicalBarRatingBar);

        review_recyclerView = getView().findViewById(R.id.reviewpage_recyclerView);
        review_recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        restaurantName_overall.setText(currentRestaurantName);

        reload();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });

    }

    public void reload(){
        reviewList = ReviewController.getAllReviewForARestaurant(this.getContext(), cb, currentRestaurantID);
        currentRestaurantName = Restaurant.get(currentRestaurantID).getName();
    }

    public void refreshReviewList(){
        reviewAdapter = new reviewRecyclerAdapter(this.getContext(), reviewList);
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
