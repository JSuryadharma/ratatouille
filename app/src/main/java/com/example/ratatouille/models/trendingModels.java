package com.example.ratatouille.models;

import com.example.ratatouille.R;

public class trendingModels {
    private int layoutModel;
    private String title;
    private String type;
    private Float rate;
    private Integer price;
    private String imageUrl;

    public trendingModels(String title, String type, Float rate, Integer price, String imageUrl) {
        this.layoutModel = R.layout.viewpager_trending; // only 1 layout model needed.
        this.title = title;
        this.type = type;
        this.rate = rate;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getLayoutModel() {
        return layoutModel;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Float getRate() {
        return rate;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
