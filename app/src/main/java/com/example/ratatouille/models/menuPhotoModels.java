package com.example.ratatouille.models;

import com.example.ratatouille.R;

public class menuPhotoModels {
    private String imageUrl;
    private int layoutModel;

    public menuPhotoModels(String imageUrl) {
        this.imageUrl = imageUrl;
        this.layoutModel = R.id.menuPhoto_viewpager;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLayoutModel() {
        return layoutModel;
    }
}
