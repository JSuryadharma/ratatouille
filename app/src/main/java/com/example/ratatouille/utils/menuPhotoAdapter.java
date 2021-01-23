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
import com.example.ratatouille.controllers.restoDetailController;
import com.example.ratatouille.models.detailPhotoModels;
import com.example.ratatouille.models.menuPhotoModels;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class menuPhotoAdapter extends PagerAdapter {
    Context context;
    ArrayList<menuPhotoModels> photoList;

    public menuPhotoAdapter(Context context, ArrayList<menuPhotoModels> photoList) {
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
        menuPhotoModels selectedItem = photoList.get(position);

        View currentView = LayoutInflater.from(context).inflate(R.layout.viewpager_detailphoto, container, false);

        PhotoView restoPhoto = currentView.findViewById(R.id.detailRestoPhoto);
        Glide.with(currentView).load(selectedItem.getImageUrl()).centerCrop().into(restoPhoto);

        restoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoDetailController.showPhotoZoomed(selectedItem.getImageUrl(), context);
            }
        });

        container.addView(currentView);
        return currentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
