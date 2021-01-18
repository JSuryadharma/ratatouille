package com.example.ratatouille.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.ratatouille.views.restaurantDetails;

import java.util.ArrayList;
import java.util.Calendar;

public class mostPopularAdapter extends RecyclerView.Adapter<mostPopularAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<mostPopularModels> mostPopularList;
    private RecyclerView mostPopularRecycler;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = mostPopularRecycler.getChildAdapterPosition(view);
            mostPopularModels t = mostPopularList.get(itemPosition);
            Intent intent = new Intent(context, restaurantDetails.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", t.getRestoId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    };

    public mostPopularAdapter(Context context, ArrayList<mostPopularModels> mostPopularList){
        this.context = context;
        this.mostPopularList = mostPopularList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mostpopular, parent, false);
        view.setOnClickListener(onClickListener);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mostPopularModels selectedItem = mostPopularList.get(position);
        holder.title.setText(selectedItem.getTitle());
        holder.types.setText(selectedItem.getType());
        holder.ratingBar.setRating(selectedItem.getRate());
        holder.openHour.setText(selectedItem.getOpenHour());
        Glide.with(holder.itemView).load(selectedItem.getImageUrl()).into(holder.mostPopularImage);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mostPopularRecycler = recyclerView;
    }

    @Override
    public int getItemCount() {
        return mostPopularList.size();
    }

    public ArrayList<mostPopularModels> getMostPopularList() {
        return mostPopularList;
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
            title.setSelected(true);
            types = itemView.findViewById(R.id.mostPopularTypes);
            ratingBar = itemView.findViewById(R.id.mostPopularRatingBar);
            openHour = itemView.findViewById(R.id.mostPopularOpenHour);
            mostPopularImage = itemView.findViewById(R.id.mostPopularImage);
        }
    }
}
