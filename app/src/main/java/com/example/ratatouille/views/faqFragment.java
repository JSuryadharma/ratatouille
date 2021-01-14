package com.example.ratatouille.views;

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

import com.example.ratatouille.R;
import com.example.ratatouille.utils.faqAdapter;
import com.example.ratatouille.utils.faqGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class faqFragment extends Fragment {
    LinearLayout backButton;
    ExpandableListView faq_list;
    TextView helpTicket;
    ArrayList<String> faq_group;
    HashMap<String, ArrayList<String>> faq_child = new HashMap<>();
    faqAdapter faqadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_faqs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.faq_backbutton);
        faq_list = view.findViewById(R.id.faq_list);
        helpTicket = view.findViewById(R.id.faq_helpTicket);

        //Initialize the FAQ List
        faq_group = faqGenerator.generateGroups();
        ArrayList<String> faq_content = faqGenerator.generateChild();

        // Loop for the group head
        for(int i=0; i<4; i++){
            ArrayList<String> eachContent = new ArrayList<>();
            eachContent.add(faq_content.get(i));

            faq_child.put(faq_group.get(i), eachContent);
        }

        faqadapter = new faqAdapter(faq_group, faq_child);
        faq_list.setAdapter(faqadapter);

        // Help Ticket Button
        helpTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // TODO: HELPTICKET DESIGN LAYOUT
//                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, ).commit();
            }
        });
    }
}
