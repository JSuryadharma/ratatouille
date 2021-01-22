package com.example.ratatouille.models;

import android.graphics.pdf.PdfDocument;

import androidx.viewpager.widget.PagerAdapter;

public class restoDetailModel {

    private String resto_id, resto_name, location, resto_type, average_price, jam_buka, menu_url, phone;
    private double rating;
    private PagerAdapter photoAdapter, menuAdapter;

    public restoDetailModel(String resto_id, String resto_name, String location, String resto_type, String average_price, String jam_buka, String menu_url, double rating, String phone, PagerAdapter photoAdapter, PagerAdapter menuAdapter) {
        this.resto_id = resto_id;
        this.resto_name = resto_name;
        this.location = location;
        this.resto_type = resto_type;
        this.average_price = average_price;
        this.jam_buka = jam_buka;
        this.menu_url = menu_url;
        this.rating = rating;
        this.photoAdapter = photoAdapter;
        this.menuAdapter = menuAdapter;
        this.phone = phone;
    }

    public String getResto_id() {
        return resto_id;
    }

    public String getResto_name() {
        return resto_name;
    }

    public String getLocation() {
        return location;
    }

    public String getResto_type() {
        return resto_type;
    }

    public String getAverage_price() {
        return average_price;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public double getRating() {
        return rating;
    }

    public PagerAdapter getPhotoAdapter() {
        return photoAdapter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhotoAdapter(PagerAdapter photoAdapter) {
        this.photoAdapter = photoAdapter;
    }

    public PagerAdapter getMenuAdapter() {
        return menuAdapter;
    }

    public void setMenuAdapter(PagerAdapter menuAdapter) {
        this.menuAdapter = menuAdapter;
    }
}
