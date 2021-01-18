package com.example.ratatouille.models;

import android.graphics.Bitmap;

import com.example.ratatouille.R;

public class detailPhotoModels {
    private Bitmap imageUrl;
    private int layoutModel;

    public detailPhotoModels(Bitmap imageUrl) {
        this.imageUrl = imageUrl;
        this.layoutModel = R.id.detailPhoto_viewpager;
    }

    public Bitmap getImageUrl() {
        return imageUrl;
    }

    public int getLayoutModel() {
        return layoutModel;
    }
}
