package com.example.ratatouille.views;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.HelpTicketController;
import com.example.ratatouille.models.HelpTicket;

import java.util.Date;

public class helpTicketFragment extends Fragment {
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView problemSubject;
    private TextView description;
    private TextView screenshot;
    private LinearLayout helpTicketButton;

    public helpTicketFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_ticket_2, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.ht_backButton);
        backButton_text = view.findViewById(R.id.ht_backButton_text);
        problemSubject = view.findViewById(R.id.ht_problemSubject);
        description = view.findViewById(R.id.ht_description);
        screenshot = view.findViewById(R.id.ht_screenshot);
        helpTicketButton = view.findViewById(R.id.ht_sendButton);

        customerView.menubar_layout.setVisibility(View.GONE);

        backButton_text.setTextColor(Color.WHITE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.GRAY);

                MediaPlayer play = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                play.start();

                Fragment backFragment = new helpTicketStartFragment();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragment_container, backFragment).commit();
            }
        });

        problemSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemSubject.setText("");
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText("");
            }
        });

        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot.setText("");
            }
        });

        helpTicketButton.setBackgroundResource(R.drawable.button_background);
        helpTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpTicketButton.setBackgroundResource(R.drawable.pressed_button_background);
                MediaPlayer play = MediaPlayer.create(getView().getContext(), R.raw.open);
                play.start();

                Date date = new Date();

                HelpTicket toBeAdded = HelpTicketController.addTicket(problemSubject.getText().toString(), description.getText().toString(), screenshot.getText().toString(), date.toString(), false);
                Fragment backFragment = new helpTicketStartFragment();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragment_container, backFragment).commit();
            }
        });
    }
}
