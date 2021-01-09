package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.models.mostPopularModels;

import java.util.ArrayList;
import java.util.Calendar;

public class mostPopularAdapter extends RecyclerView.Adapter<mostPopularAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<mostPopularModels> mostPopularList;

    public mostPopularAdapter(Context context, ArrayList<mostPopularModels> mostPopularList){
        this.context = context;
        this.mostPopularList = mostPopularList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mostpopular, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mostPopularModels selectedItem = mostPopularList.get(position);
        holder.title.setText(selectedItem.getTitle());
        holder.types.setText(selectedItem.getType());
        holder.ratingBar.setRating(selectedItem.getRate());
        String hours = String.valueOf(selectedItem.getOpenHour().get(Calendar.HOUR));
        String minutes = String.valueOf(selectedItem.getOpenHour().get(Calendar.MINUTE));
        if(selectedItem.getOpenHour().get(Calendar.HOUR) < 10){
            hours = "0" + hours;
        }
        if(selectedItem.getOpenHour().get(Calendar.MINUTE) < 10){
            minutes = "0" + minutes;
        }
        holder.openHour.setText("Opens at " + hours + "." + minutes);
        Glide.with(holder.itemView).load(selectedItem.getImageUrl()).into(holder.mostPopularImage);
    }

    @Override
    public int getItemCount() {
        return mostPopularList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView types;
        RatingBar ratingBar;
        TextView openHour;
        ImageView mostPopularImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mostPopularTitle);
            types = itemView.findViewById(R.id.mostPopularTypes);
            ratingBar = itemView.findViewById(R.id.mostPopularRatingBar);
            openHour = itemView.findViewById(R.id.mostPopularOpenHour);
            mostPopularImage = itemView.findViewById(R.id.mostPopularImage);
        }
    }
}
