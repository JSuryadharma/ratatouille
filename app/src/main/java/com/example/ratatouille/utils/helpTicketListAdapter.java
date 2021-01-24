package com.example.ratatouille.utils;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.EmployeeController;
import com.example.ratatouille.models.Employee;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.views.helpTicketDetailsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class helpTicketListAdapter extends RecyclerView.Adapter<helpTicketListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HelpTicket> helpTicketList;
    private FragmentManager parentFragmentManager;

    public helpTicketListAdapter(Context context, ArrayList<HelpTicket> helpTicketList, FragmentManager parentFragmentManager) {
        this.context = context;
        this.helpTicketList = helpTicketList;
        this.parentFragmentManager = parentFragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_helpticketlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HelpTicket ticket = helpTicketList.get(position);

        holder.ticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.open);
                player.start();
                Fragment helpTicketDetailsFragment = new helpTicketDetailsFragment(ticket);
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, helpTicketDetailsFragment).commit();
            }
        });

        holder.helpTicketID.setText(ticket.getProblemSubject());
        holder.helpTicketaskDate.setText("Date: " + ticket.getAskHelpDate());
        if(ticket.getIsSolved() == false) {
            holder.helpTicketIndicator.setBackgroundResource(R.drawable.helpticket_indicator_red);
        } else {
            holder.helpTicketIndicator.setBackgroundResource(R.drawable.helpticket_indicator_green);
        }
    }

    @Override
    public int getItemCount() {
        return helpTicketList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout ticketLayout;
        private TextView helpTicketID;
        private TextView helpTicketaskDate;
        private LinearLayout helpTicketIndicator;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketLayout = itemView.findViewById(R.id.helpticketlist_area);
            helpTicketID = itemView.findViewById(R.id.helpticketlist_title);
            helpTicketaskDate = itemView.findViewById(R.id.helpticketlist_date);
            helpTicketIndicator = itemView.findViewById(R.id.helpticketlist_indicator);
        }
    }
}
