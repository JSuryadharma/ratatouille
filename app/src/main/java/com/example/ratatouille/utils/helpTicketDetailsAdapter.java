package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.EmployeeController;
import com.example.ratatouille.models.Employee;
import com.example.ratatouille.models.HelpTicketDetails;

import java.util.ArrayList;

public class helpTicketDetailsAdapter extends RecyclerView.Adapter<helpTicketDetailsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HelpTicketDetails> helpTicketDetails;

    public helpTicketDetailsAdapter(Context context, ArrayList<HelpTicketDetails> helpTicketDetails) {
        this.context = context;
        this.helpTicketDetails = helpTicketDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_helpticketdetails, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HelpTicketDetails details = helpTicketDetails.get(position);
        holder.helpTicketID.setText(details.getMessageID());
        holder.helpTicketMessage.setText(details.getMessage());
        holder.helpTicketEmployeeReply.setText(EmployeeController.getAEmployee(details.getEmployeeID()).getEmployeeName());
    }

    @Override
    public int getItemCount() {
        return helpTicketDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView helpTicketID;
        private TextView helpTicketMessage;
        private TextView helpTicketEmployeeReply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            helpTicketID = itemView.findViewById(R.id.helpticketdetails_title);
            helpTicketMessage = itemView.findViewById(R.id.helpticketdetails_message);
            helpTicketEmployeeReply = itemView.findViewById(R.id.helpticketdetails_employeeReply);
        }
    }
}
