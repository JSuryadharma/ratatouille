package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.models.AboutUs;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class aboutUsAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<AboutUs> aboutUsData;

    public aboutUsAdapter(Context context, ArrayList<AboutUs> aboutUsData) {
        this.context = context;
        this.aboutUsData = aboutUsData;
    }

    @Override
    public int getCount() {
        return aboutUsData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        AboutUs currentItem = aboutUsData.get(position);

        System.out.println("Ceking: " + currentItem.getName());

        View currentView = LayoutInflater.from(context).inflate(R.layout.card_aboutus, container, false);

        CircleImageView profilePicture = currentView.findViewById(R.id.au_profilePicture);
        TextView profileName = currentView.findViewById(R.id.au_name);
        TextView profileDescription = currentView.findViewById(R.id.au_description);

        profileName.setText(currentItem.getName());
        profileDescription.setText(currentItem.getDescription());

        Glide.with(currentView).load(currentItem.getProfilePicture()).fitCenter().into(profilePicture);

        container.addView(currentView);

        return currentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
