package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.HelpTicketController;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.models.HelpTicketDetails;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;
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
        View view  = LayoutInflater.from(context).inflate(R.layout.viewpager_helpticketlist, container,false);

        TextView helpTicketTitle = view.findViewById(R.id.helpticketlist_title);
        TextView helpTicketDescription = view.findViewById(R.id.helpticketlist_description);
        LinearLayout helpTicketButton = view.findViewById(R.id.helpticketlist_button);
        TextView helpTicketDate = view.findViewById(R.id.helpticketlist_date);

        //Setting view components..
        HelpTicket selectedTicket = helpTicketList.get(position);
        helpTicketTitle.setText(selectedTicket.getProblemSubject());
        helpTicketDescription.setText(selectedTicket.getDescription());
        helpTicketDate.setText(selectedTicket.getAskHelpDate());

        helpTicketButton.setBackgroundResource(R.drawable.round_button);

        helpTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpTicketStartFragment.selectedticketID = selectedTicket.getTicketID();
                for (int i = 0; i < helpTicketList.size(); i++) {
                    if (touched.get(i) == true && position != i) {
                        Toast.makeText(context, "You have selected a ticket!", Toast.LENGTH_LONG);
                        Utils.showDialogMessage(R.drawable.ic_warning, context, "Cannot Select Item", "You already have selected an Item!\n Please unselect an item.");
                        return;
                    }
                }

                if (touched.get(position) == false) {
                    helpTicketButton.setBackgroundResource(R.drawable.voucherbutton_pressed_background);
                    touched.set(position, true);
                    System.out.println("onpressed: " + position);
                } else if (touched.get(position) == true) {
                    helpTicketButton.setBackgroundResource(R.drawable.voucherbutton_background);
                    touched.set(position, false);
                    System.out.println("offpressed: " + position);
                }
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
