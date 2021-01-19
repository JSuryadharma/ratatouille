package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.models.trendingModels;

import java.util.ArrayList;

public class trendingAdapter extends PagerAdapter {
    Context context;
    ArrayList<trendingModels> trendingList;

    public trendingAdapter(Context context, ArrayList<trendingModels> trendingList) {
        this.context = context;
        this.trendingList = trendingList;
    }

    @Override
    public int getCount() {
        return trendingList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        trendingModels selectedItem = trendingList.get(position);

        View currentView = LayoutInflater.from(context).inflate(selectedItem.getLayoutModel(), container, false);

        TextView title = currentView.findViewById(R.id.trendingTitle);
        TextView types = currentView.findViewById(R.id.trendingTypes);
        RatingBar rating = currentView.findViewById(R.id.trendingRatingBar);
        TextView price = currentView.findViewById(R.id.trendingPrice);
        ImageView trendingImage = currentView.findViewById(R.id.trendingImage);

        title.setText(selectedItem.getTitle());
        types.setText("Type: " + selectedItem.getType());
        rating.setRating(selectedItem.getRate());
        price.setText("Rp. " + selectedItem.getPrice().toString());

        Glide.with(currentView).load(selectedItem.getImageUrl()).into(trendingImage);

        container.addView(currentView);

        return currentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
