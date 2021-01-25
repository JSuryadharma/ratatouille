package com.example.ratatouille.views;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.faqAdapter;
import com.example.ratatouille.utils.faqGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class faqFragment extends Fragment {
    private SwipeRefreshLayout pulltorefresh;
    private LinearLayout backButton;
    private TextView backButton_text;
    private EditText searchBar;
    private ExpandableListView faq_list;
    private TextView helpTicket;
    private ArrayList<String> faq_group;
    private HashMap<String, String> faq_child = new HashMap<>();
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
        searchBar = view.findViewById(R.id.faq_searchbar);
        faq_list = view.findViewById(R.id.faq_list);
        helpTicket = view.findViewById(R.id.faq_helpTicket);
        backButton_text = view.findViewById(R.id.faq_backbutton_text);
        reload();

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

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchBar.getText().toString().equals("")){
                    reload();
                    return;
                }
                ArrayList<String> searchResult = new ArrayList<>();
                for(String eachData : faq_group){
                    if(Utils.matchString(eachData, searchBar.getText().toString())){
                        searchResult.add(eachData);
                    }
                }
                showSearchResult(searchResult);
            }
        });

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
                Fragment customerSupportFragment = new customerSupportFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, customerSupportFragment).commit();
            }
        });
    }

    public void reload() {
        faq_group = faqGenerator.generateGroups();
        faq_content = faqGenerator.generateChild();
        faq_child = new HashMap<>();
        System.out.println(faq_content.get(0));
        // Loop for the group head
        faq_child.put(faq_group.get(0), faq_content.get(0));

        faq_child.put(faq_group.get(1), faq_content.get(1));

        faq_child.put(faq_group.get(2), faq_content.get(2));

        faq_child.put(faq_group.get(3), faq_content.get(3));

        faqadapter = new faqAdapter(faq_group, faq_child);
        faq_list.setAdapter(faqadapter);
    }

    public void showSearchResult(ArrayList<String> searchResult) {
        faq_child = new HashMap<>();
        Integer k = 0;
        for(int i=0; i<searchResult.size(); i++){
            Integer idx = faq_group.indexOf(searchResult.get(i));
            faq_child.put(searchResult.get(i), faq_content.get(idx));
        }
        faqadapter = new faqAdapter(searchResult, faq_child);
        faq_list.setAdapter(faqadapter);
    }

}
