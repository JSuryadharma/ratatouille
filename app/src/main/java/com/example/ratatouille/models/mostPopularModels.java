package com.example.ratatouille.models;

import com.example.ratatouille.R;

import java.util.Calendar;
import java.util.Date;

public class mostPopularModels {
    private int layoutModel;
    private String title;
    private String type;
    private Float rate;
    private String openHour;
    private String imageUrl;

    public mostPopularModels(String title, String type, Float rate, String openHour, String imageUrl) {
        this.layoutModel = R.id.trending_viewPager; // only 1 layout model needed.
        this.title = title;
        this.type = type;
        this.rate = rate;
        this.openHour = openHour;
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

    public String getOpenHour() {
        return openHour;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
