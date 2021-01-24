package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.models.HelpTicketDetails;

import java.util.ArrayList;

public class helpTicketDetailAdapter extends RecyclerView.Adapter<helpTicketDetailAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HelpTicketDetails> ticketDetails;

    public helpTicketDetailAdapter(Context context, ArrayList<HelpTicketDetails> ticketDetails){
        this.context = context;
        this.ticketDetails = ticketDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_helpticketdetails, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HelpTicketDetails currentDetail = ticketDetails.get(position);

        holder.helpTicketID.setText("Message ID: " + currentDetail.getMessageID());
        holder.helpTicketMessage.setText("Message:\n" + currentDetail.getMessage());
        holder.helpTicketReply.setText("Replied By: " + currentDetail.getUserName());
    }

    @Override
    public int getItemCount() {
        return ticketDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView helpTicketID;
        private TextView helpTicketMessage;
        private TextView helpTicketReply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            helpTicketID = itemView.findViewById(R.id.helpticketdetails_title);
            helpTicketMessage = itemView.findViewById(R.id.helpticketdetails_message);
            helpTicketReply = itemView.findViewById(R.id.helpticketdetails_employeeReply);
        }
    }
}
