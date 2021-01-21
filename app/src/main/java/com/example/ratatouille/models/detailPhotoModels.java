package com.example.ratatouille.models;

import android.graphics.Bitmap;

import com.example.ratatouille.R;

public class detailPhotoModels {
    private String imageUrl;
    private int layoutModel;

    public detailPhotoModels(String imageUrl) {
        this.imageUrl = imageUrl;
        this.layoutModel = R.id.detailPhoto_viewpager;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLayoutModel() {
        return layoutModel;
    }
}
