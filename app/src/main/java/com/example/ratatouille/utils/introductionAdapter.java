package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ratatouille.R;
import com.example.ratatouille.models.introModels;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class introductionAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<introModels> items = new ArrayList<introModels>();

    public introductionAdapter(Context context){
        this.context = context;
        // make sure that arraylist only having 1 item, we dont want to add duplicate items..
        introModels item1 = new introModels(R.layout.activity_introduction1_recreate);
        items.add(item1);
        introModels item2 = new introModels(R.layout.activity_introduction2_recreate);
        items.add(item2);
        introModels item3 = new introModels(R.layout.activity_introduction3_recreate);
        items.add(item3);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        introModels selectedItem = items.get(position);

        //Inflate layout from card_item.xml
        View view = LayoutInflater.from(context).inflate(selectedItem.getLayoutModel(), container, false);

        //Add view to the container
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
