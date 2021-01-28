package com.example.ratatouille.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.FavouritesController;
import com.example.ratatouille.controllers.restoDetailController;
import com.example.ratatouille.models.Favourite;
import com.example.ratatouille.models.Review;
import com.example.ratatouille.models.mostPopularModels;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.restaurantDetails;

import java.util.ArrayList;

public class favouritesRecyclerAdapter extends RecyclerView.Adapter<favouritesRecyclerAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Favourite> favouritesList;

    public favouritesRecyclerAdapter(Context context, ArrayList<Favourite> reviewList) {
        this.context = context;
        this.favouritesList = reviewList;
    }

    @NonNull
    @Override
    public favouritesRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_favourites, parent, false);

        CardView cardView = view.findViewById(R.id.recycler_cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, restaurantDetails.class);
                restoDetailController.query(context, intent, VariablesUsed.currentRestoDetail.getResto_id());
            }
        });

        return new favouritesRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favouritesRecyclerAdapter.MyViewHolder holder, int position) {
        Favourite selectedItems = favouritesList.get(position);
        holder.title.setText(selectedItems.getName());
        holder.types.setText(selectedItems.getType());
        holder.price.setText(selectedItems.getPrice());
        holder.ratingBar.setRating(selectedItems.getRating().floatValue());
        holder.angka.setText(String.valueOf(selectedItems.getRating().floatValue()));
        String[] id = selectedItems.getFavouriteID().split("-");
        holder.page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, restaurantDetails.class);
                restoDetailController.query(context, intent, id[1]);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favourite.delete(VariablesUsed.loggedUser.getUid(), selectedItems.getFavouriteID());
            }
        });
        Glide.with(holder.itemView).load(selectedItems.getPhotoUrl()).fitCenter().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, types, price, angka, deleteButton;
        RatingBar ratingBar;
        CardView page;
        ImageView imageView;

        //Inisialisasi variabel, for view components
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteButton = itemView.findViewById(R.id.favourite_deleteButton);
            title = itemView.findViewById(R.id.favourites_restotitle);
            types = itemView.findViewById(R.id.favourites_restotypes);
            price = itemView.findViewById(R.id.favourites_restoprice);
            ratingBar = itemView.findViewById(R.id.favourites_ratingbar);
            angka = itemView.findViewById(R.id.favourites_angka);
            page = itemView.findViewById(R.id.recycler_cardview);
            imageView = itemView.findViewById(R.id.favourites_restoimage);
        }
    }
}
