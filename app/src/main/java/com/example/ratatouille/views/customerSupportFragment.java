package com.example.ratatouille.views;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.vars.VariablesUsed;

public class customerSupportFragment extends Fragment {
    private LinearLayout backButton;
    private TextView usernameLabel;
    private RelativeLayout createHelpTicketButton;
    private RelativeLayout helpTicketListButton;

    public customerSupportFragment() {
        //empty constructor.. required
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.cs_backButton);
        usernameLabel = view.findViewById(R.id.cs_username);
        createHelpTicketButton = view.findViewById(R.id.cs_helpticketcreate_button);
        helpTicketListButton = view.findViewById(R.id.cs_helpticketlist_button);

        usernameLabel.setText("Hello, " + VariablesUsed.currentUser.getUsername() + "!");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment backFragment = new faqFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backFragment).commit();
            }
        });

        createHelpTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createHelpTicketFragment = new helpTicketCreateFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, createHelpTicketFragment).commit();
            }
        });

        helpTicketListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpTicketListFragment = new helpTicketListFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, helpTicketListFragment).commit();
            }
        });
    }
}
