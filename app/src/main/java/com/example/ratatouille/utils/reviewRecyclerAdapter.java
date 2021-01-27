package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.models.Review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class reviewRecyclerAdapter extends RecyclerView.Adapter<reviewRecyclerAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Review> reviewList;

    public reviewRecyclerAdapter(Context context, ArrayList<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_reviews, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Review selectedItems = reviewList.get(position);
        holder.username.setText(selectedItems.getCustomerUsername());
        Double averageCalculation =
                selectedItems.getMaskRate() + selectedItems.getPhysicalBarriersRate() + selectedItems.getSanitizeRate()
                + selectedItems.getSocialDistancingRate() + selectedItems.getTemperatureRate();
        averageCalculation = averageCalculation / 5;
        holder.avg.setText("(" + averageCalculation.toString() + ")");
        holder.ratingBar.setRating(averageCalculation.floatValue());
        holder.message.setText("Message: \n" + selectedItems.getDescription());
        holder.submitDate.setText("Submit Date : " + selectedItems.getSubmitDate());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView message;
        TextView avg;
        RatingBar ratingBar;
        TextView submitDate;

        //Inisialisasi variabel, for view components
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.reviewlist_name);
            message = itemView.findViewById(R.id.reviewlist_message);
            ratingBar = itemView.findViewById(R.id.reviewlist_ratingBar);
            submitDate = itemView.findViewById(R.id.reviewlist_date);
            avg = itemView.findViewById(R.id.reviewlist_avg);
        }
    }
}
