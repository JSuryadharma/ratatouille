package com.example.ratatouille.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ratatouille.R;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.views.helpTicketStartFragment;

import java.util.ArrayList;

public class helpTicketListAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<HelpTicket> helpTicketList;
    private static ArrayList<Boolean> touched = new ArrayList<>();

    public helpTicketListAdapter(Context context, ArrayList<HelpTicket> helpTicketList) {
        this.context = context;
        this.helpTicketList = helpTicketList;
        for(int i=0; i<helpTicketList.size(); i++){
            touched.add(false);
        }
    }

    @Override
    public int getCount() {
        return helpTicketList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recyclerview_helpticketlist, container,false);

        TextView helpTicketTitle = view.findViewById(R.id.helpticketlist_title);
        LinearLayout helpTicketIndicator = view.findViewById(R.id.helpticketlist_button);
        TextView helpTicketDate = view.findViewById(R.id.helpticketlist_date);

        //Setting view components..
        HelpTicket selectedTicket = helpTicketList.get(position);
        helpTicketTitle.setText(selectedTicket.getProblemSubject());
        if(selectedTicket.getIsSolved() == false){
            helpTicketIndicator.setBackgroundColor(Color.RED);
        } else {
            helpTicketIndicator.setBackgroundColor(Color.GREEN);
        }
        helpTicketDate.setText(selectedTicket.getAskHelpDate());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
