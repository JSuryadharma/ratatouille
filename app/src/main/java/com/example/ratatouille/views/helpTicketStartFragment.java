package com.example.ratatouille.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.EmployeeController;
import com.example.ratatouille.controllers.HelpTicketController;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.models.HelpTicketDetails;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.helpTicketDetailsAdapter;
import com.example.ratatouille.utils.helpTicketListAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;
import java.util.UUID;

public class helpTicketStartFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private LinearLayout backButton;
    private LinearLayout addButton;
    private ViewPager helpTicketLists;
    private RecyclerView helpTicketDetails;
    private ArrayList<HelpTicket> ticketDatas;
    private ArrayList<HelpTicketDetails> ticketDetails;
    private helpTicketListAdapter ticketListAdapter;
    private helpTicketDetailsAdapter ticketDetailsAdapter;
    public static String selectedticketID = "";
    private static Handler handler = null;
    private static Runnable runnable = null;

    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            if(ticketDatas != null && ticketDatas.size() > 0) {
                for(HelpTicket td : ticketDatas){
                    System.out.println("Masuk List: " + td.getProblemSubject());
                }
                refreshList();
            }
            if(ticketDetails != null && ticketDetails.size() > 0){
                for(HelpTicketDetails td : ticketDetails){
                    System.out.println("Masuk Detil: " + td.getMessage());
                }
                refreshDetails();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_helpticket_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh = view.findViewById(R.id.helpticket_start_refresh);
        backButton = view.findViewById(R.id.helpticket_start_backButton);
        addButton = view.findViewById(R.id.helpticket_start_addButton);
        helpTicketLists = view.findViewById(R.id.helpticket_start_viewPager);
        helpTicketDetails = view.findViewById(R.id.helpticket_start_recyclerView);
        ticketDatas = new ArrayList<>();
        ticketDetails = new ArrayList<>();

        helpTicketDetails.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        EmployeeController.createEmployee("Susana", "susan123", "085155154344", "Jalan Waru nomer 5");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment backFragment = new faqFragment();
                getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragment_container, backFragment).commit();
                handler.removeCallbacks(runnable);
            }
        });

        addButton.setBackgroundResource(R.drawable.round_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setBackgroundResource(R.drawable.pressed_round_button);
                Fragment addTicketFragment = new helpTicketFragment();
                getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragment_container, addTicketFragment).commit();
                handler.removeCallbacks(runnable);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                refresh.setRefreshing(false);
            }
        });
        reload();
        createRefreshHandler();
    }

    public void createRefreshHandler() {
        if(handler != null){
            return;
        }
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                reload();
            }
        };
        handler.postDelayed(runnable, 60000);
    }

    public void reload() {
        ticketDatas = HelpTicketController.getAllTicket(getView().getContext(), cb, VariablesUsed.loggedUser.getUid());
        System.out.println("reload check: " + selectedticketID);
        if(!selectedticketID.equals("")){
            ticketDetails = HelpTicketController.getTicketDetails(getView().getContext(), cb, selectedticketID);
        }
    }

    public void refreshList() {
        ticketListAdapter = new helpTicketListAdapter(getView().getContext(), ticketDatas);
        helpTicketLists.setAdapter(ticketListAdapter);
    }

    public void refreshDetails() {
        ticketDetailsAdapter = new helpTicketDetailsAdapter(getView().getContext(), ticketDetails);
        helpTicketDetails.setAdapter(ticketDetailsAdapter);
    }
}
