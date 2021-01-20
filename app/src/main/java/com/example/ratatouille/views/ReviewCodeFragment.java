package com.example.ratatouille.views;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;

public class ReviewCodeFragment extends Fragment {
    private String currentRestaurantID = "";
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView nextButton;
    private EditText inputReviewCode;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            validAndVerified();
        }
    };

    public ReviewCodeFragment(String currentRestaurantID) {
        this.currentRestaurantID = currentRestaurantID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = getView().findViewById(R.id.reviewcode_backButton);
        backButton_text = getView().findViewById(R.id.reviewcode_backButton_text);
        nextButton = getView().findViewById(R.id.reviewcode_nextButton);
        inputReviewCode = getView().findViewById(R.id.reviewcode_code);

        backButton_text.setTextColor(Color.BLACK);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                Fragment backFragment = new reviewFragment(currentRestaurantID);

                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();

//              TODO: KALAU DETAILS SUDAH JADI, JANGAN LUPA DIISI INI..
//              getParentFragmentManager().beginTransaction().replace()
            }
        });

        nextButton.setTextColor(Color.BLACK);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputReviewCode == null || inputReviewCode.getText().toString().equals("")){
                    Utils.showDialogMessage(R.drawable.ic_warning, view.getContext(), "Unauthorized Action", "Please fill in your RVC Code!");
                    return;
                }
                nextButton.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                ReviewController.checkReviewVerification(view.getContext(), cb, inputReviewCode.getText().toString(), currentRestaurantID);
            }
        });
    }

    public void validAndVerified() {
        Fragment nextFragment = new reviewCreateFragment(currentRestaurantID);
//        TODO: KALAU DETAILS SUDAH JADI, JANGAN LUPA DIISI INI..
//        getParentFragmentManager().beginTransaction().replace()
    }
}
