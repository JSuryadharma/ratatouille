package com.example.ratatouille.views;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.utils.faqAdapter;
import com.example.ratatouille.utils.faqGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class faqFragment extends Fragment {
    private SwipeRefreshLayout pulltorefresh;
    private LinearLayout backButton;
    private TextView backButton_text;
    private ExpandableListView faq_list;
    private TextView helpTicket;
    private ArrayList<String> faq_group;
    private HashMap<String, ArrayList<String>> faq_child = new HashMap<>();
    private faqAdapter faqadapter;
    private ArrayList<String> faq_content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_faqs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pulltorefresh = view.findViewById(R.id.faq_pulltorefresh);
        backButton = view.findViewById(R.id.faq_backbutton);
        faq_list = view.findViewById(R.id.faq_list);
        helpTicket = view.findViewById(R.id.faq_helpTicket);
        backButton_text = view.findViewById(R.id.faq_backbutton_text);
        faq_group = faqGenerator.generateGroups();
        faq_content = faqGenerator.generateChild();

        customerView.menubar_layout.setVisibility(View.GONE);

        backButton_text.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                Fragment backFragment = new ViewProfileFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, backFragment).commit();
            }
        });

        reload();

        pulltorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                pulltorefresh.setRefreshing(false);
            }
        });


        // Help Ticket Button
        helpTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpTicketStartFragment = new helpTicketStartFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, helpTicketStartFragment).commit();
            }
        });
    }

    public void reload() {
        //Initialize the FAQ List
        faq_group = faqGenerator.generateGroups();
        faq_content = faqGenerator.generateChild();

        // Loop for the group head
        for(int i=0; i<4; i++){
            ArrayList<String> eachContent = new ArrayList<>();
            eachContent.add(faq_content.get(i));

            faq_child.put(faq_group.get(i), eachContent);
        }
        faqadapter = new faqAdapter(faq_group, faq_child);
        faq_list.setAdapter(faqadapter);
    }

}
