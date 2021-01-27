package com.example.ratatouille.views;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.HelpTicketController;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.models.HelpTicketDetails;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.helpTicketDetailAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class helpTicketDetailsFragment extends Fragment {
    private HelpTicket helpTicket;
    private SwipeRefreshLayout refresh;
    private LinearLayout backButton;
    private TextView backButtonText;
    private TextView ticketTitle;
    private RecyclerView ticketDetails_RecyclerView;
    private LinearLayout closeButton;
    private EditText textMessage;
    private LinearLayout sendButton;
    private ScrollView scrollView;

    private ArrayList<HelpTicketDetails> ticketDetails;

    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            setRecyclerView();
        }
    };

    public helpTicketDetailsFragment(HelpTicket helpTicket) {
        this.helpTicket = helpTicket;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_helpticket_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollView = view.findViewById(R.id.helpticket_details_scrollview);
        refresh = view.findViewById(R.id.helpticket_details_refresh);
        backButton = view.findViewById(R.id.helpticket_details_backButton);
        backButtonText = view.findViewById(R.id.helpticket_details_backButton_text);
        ticketTitle = view.findViewById(R.id.helpticket_details_title);
        ticketDetails_RecyclerView = view.findViewById(R.id.helpticket_details_recyclerView);
        closeButton = view.findViewById(R.id.helpticket_details_closeButton);
        textMessage = view.findViewById(R.id.helpticket_details_editText);
        sendButton = view.findViewById(R.id.helpticket_details_sendButton);

        backButtonText.setTextColor(Color.WHITE);
        ticketDetails = new ArrayList<>();

        Utils.response dialogRespClose = new Utils.response() {
            @Override
            public void yesResponse() {
                HelpTicketController.closeHelpTicket(helpTicket);
                Toast.makeText(view.getContext(), "Ticket Closed Successfully.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void noResponse() {
                Toast.makeText(view.getContext(), "Action Cancelled.", Toast.LENGTH_LONG).show();
            }
        };

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                refresh.setRefreshing(false);
            }
        });

        ticketTitle.setText(this.helpTicket.getProblemSubject());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonText.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(view.getContext(), R.raw.personleave);
                Fragment backFragment = new helpTicketListFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backFragment).commit();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showOptMessage(view.getContext(), dialogRespClose, "Confirmation", "Are you sure to close Ticket ID:\n" + helpTicket.getTicketID());
            }
        });

        if(helpTicket.getIsSolved() == false) {
            textMessage.setEnabled(true);
            closeButton.setEnabled(true);
        } else {
            textMessage.setEnabled(false);
            closeButton.setEnabled(false);
            closeButton.setBackgroundResource(R.drawable.pressed_round_button);
        }

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0){
                    refresh.setEnabled(true);
                } else {
                    refresh.setEnabled(false);
                }
            }
        });

        Utils.response dialogRespSend = new Utils.response() {
            @Override
            public void yesResponse() {
                HelpTicketController.addDetails(helpTicket.getTicketID(), VariablesUsed.loggedUser.getUid(), VariablesUsed.currentUser.getUsername(), textMessage.getText().toString());

                MediaPlayer player = MediaPlayer.create(view.getContext(), R.raw.send_message);
                player.start();

                textMessage.setText("");

                Toast.makeText(view.getContext(), "Details added succesfully.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void noResponse() {
                Toast.makeText(view.getContext(), "Action cancelled.", Toast.LENGTH_LONG).show();
            }
        };

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpTicket.getIsSolved() == true){
                    Toast.makeText(view.getContext(), "HelpTicket ID: " + helpTicket.getTicketID() + " is already solved!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Utils.validateInput(textMessage.getText().toString())){
                    Utils.showOptMessage(view.getContext(), dialogRespSend, "Confirmation", "Are you sure to add this message\n to your HelpTicket ID:\n" + helpTicket.getTicketID());
                } else {
                    Toast.makeText(view.getContext(), "Input invalid.", Toast.LENGTH_LONG).show();
                    textMessage.setError("Input cannot be empty.");
                }
            }
        });

        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                reload();
            }
        };
        handler.postDelayed(run, 60000);
    }

    private void reload() {
        ticketDetails = HelpTicketController.getTicketDetails(this.getContext(), cb, helpTicket.getTicketID());
        Collections.sort(ticketDetails, new Comparator<HelpTicketDetails>() {
            @Override
            public int compare(HelpTicketDetails o1, HelpTicketDetails o2) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date time1 = null;
                Date time2 = null;
                try {
                    time1 = df.parse(o1.getSubmitDate());
                    time2 = df.parse(o2.getSubmitDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (time1 == null || time2 == null) return 0;

                return time1.compareTo(time2);
            }
        });
    }

    private void setRecyclerView(){
        helpTicketDetailAdapter detailAdapter = new helpTicketDetailAdapter(this.getContext(), ticketDetails);
        ticketDetails_RecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ticketDetails_RecyclerView.setAdapter(detailAdapter);
    }
}
