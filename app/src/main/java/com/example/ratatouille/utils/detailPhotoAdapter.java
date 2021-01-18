package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.models.detailPhotoModels;

import java.util.ArrayList;

public class detailPhotoAdapter extends PagerAdapter {
    Context context;
    ArrayList<detailPhotoModels> photoList;

    public detailPhotoAdapter(Context context, ArrayList<detailPhotoModels> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        detailPhotoModels selectedItem = photoList.get(position);

        View currentView = LayoutInflater.from(context).inflate(selectedItem.getLayoutModel(), container, false);

        ImageView restoPhoto = currentView.findViewById(R.id.detailRestoPhoto);
        Glide.with(currentView).load(selectedItem.getImageUrl()).into(restoPhoto);

        container.addView(currentView);
        return currentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
