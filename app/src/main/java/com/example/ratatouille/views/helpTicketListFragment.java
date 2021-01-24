package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.EmployeeController;
import com.example.ratatouille.controllers.HelpTicketController;
import com.example.ratatouille.models.HelpTicket;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.helpTicketListAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;

import static com.example.ratatouille.views.customerView.menubar_layout;

public class helpTicketListFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private LinearLayout backButton;
    private TextView backButtonText;
    private TextView ticketAmount;

    private RecyclerView unsolved_RecyclerView;
    private RecyclerView solved_RecyclerView;

    private ArrayList<HelpTicket> helpTicketList;
    private ArrayList<HelpTicket> unsolvedList;
    private ArrayList<HelpTicket> solvedList;

    private helpTicketListAdapter helpTicketUnsolvedAdapter;
    private helpTicketListAdapter helpTicketSolvedAdapter;

    private FragmentManager currentParent;

    private static Handler handler = null;
    private static Runnable runnable = null;

    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            fillInUnsolvedList();
            fillInSolvedList();
            setRecyclerView();
        }
    };

    private void reload() {
        helpTicketList = HelpTicketController.getTicketList(this.getContext(), cb, VariablesUsed.loggedUser.getUid());
    }

    private void fillInSolvedList() {
        solvedList = new ArrayList<>();
        for (HelpTicket curHelpTicket : helpTicketList){
            if(curHelpTicket.getIsSolved() == true) {
                solvedList.add(curHelpTicket);
            }
        }
    }

    private void fillInUnsolvedList() {
        unsolvedList = new ArrayList<>();
        for(HelpTicket curHelpTicket : helpTicketList){
            if(curHelpTicket.getIsSolved() == false) {
                unsolvedList.add(curHelpTicket);
            }
        }
    }

    private void setRecyclerView() {
        ticketAmount.setText(helpTicketList.size() + " Tickets");
        helpTicketUnsolvedAdapter = new helpTicketListAdapter(this.getContext(), unsolvedList, currentParent);
        helpTicketSolvedAdapter = new helpTicketListAdapter(this.getContext(), solvedList, currentParent);
        unsolved_RecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        solved_RecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        unsolved_RecyclerView.setAdapter(helpTicketUnsolvedAdapter);
        solved_RecyclerView.setAdapter(helpTicketSolvedAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_helpticket_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentParent = getParentFragmentManager();
        refresh = view.findViewById(R.id.helpticket_list_refresh);
        backButton = view.findViewById(R.id.helpticket_list_backButton);
        backButtonText = view.findViewById(R.id.helpticket_list_backButton_text);
        ticketAmount = view.findViewById(R.id.helpticket_list_amountTicket);

        unsolved_RecyclerView = view.findViewById(R.id.helpticket_list_unsolvedviewPager);
        solved_RecyclerView = view.findViewById(R.id.helpticket_list_solvedviewPager);

        unsolvedList = new ArrayList<>(); // for initializing..
        solvedList = new ArrayList<>(); // for initializing..

        helpTicketList = new ArrayList<>(); // for initializing..
        helpTicketList = HelpTicketController.getTicketList(view.getContext(), cb, VariablesUsed.loggedUser.getUid());

        backButtonText.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonText.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                Fragment backIntent = new customerSupportFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backIntent).commit();
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                Fragment backIntent = new customerSupportFragment();
                refresh.setRefreshing(false);
            }
        });

        reload();
        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                reload();
            }
        };
        handler.postDelayed(run, 60000);
    }
}
