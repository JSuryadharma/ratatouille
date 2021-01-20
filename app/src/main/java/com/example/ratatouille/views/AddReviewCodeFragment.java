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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.models.ReviewVerification;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;

public class AddReviewCodeFragment extends Fragment {
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView userID;
    private EditText restaurantIDInput;
    private EditText customerIDInput;
    private RelativeLayout generateButton;
    private TextView generatedCode;
    public static ReviewVerification verifier;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            updateGeneratedCode();
        }
    };

    public AddReviewCodeFragment(){
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_review_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = getView().findViewById(R.id.addreviewcode_backButton);
        backButton_text = getView().findViewById(R.id.addreviewcode_backButton_text);
        userID = getView().findViewById(R.id.addreviewcode_userid);
        restaurantIDInput = getView().findViewById(R.id.addreviewcode_restaurantID);
        customerIDInput = getView().findViewById(R.id.addreviewcode_customerID);
        generateButton = getView().findViewById(R.id.addreviewcode_generateButton);
        generatedCode = getView().findViewById(R.id.addreviewcode_code);

        customerView.menubar_layout.setVisibility(View.GONE);

        backButton_text.setTextColor(Color.BLACK);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);

                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();

                Fragment backFragment = new ViewProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backFragment).commit();
            }
        });

        userID.setText(VariablesUsed.loggedUser.getUid().toString());

        generatedCode.setText("xxxxxxxxxxxxxxxx");

        generateButton.setBackgroundResource(R.drawable.round_button);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateButton.setBackgroundResource(R.drawable.pressed_round_button);
                if(!Utils.validateInput(restaurantIDInput.getText().toString()) || !Utils.validateInput(customerIDInput.getText().toString())){
                    restaurantIDInput.setError("Invalid Input");
                    customerIDInput.setError("Invalid Input");
                    return;
                }
                verifier = ReviewController.addVerificationCode(getView().getContext(), cb, restaurantIDInput.getText().toString(), customerIDInput.getText().toString());
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Review Code Generated!", "Please show the code to the customer. Do not leave this page if the code hasn't been used.");
            }
        });
    }

    public void updateGeneratedCode() {
        if(verifier == null) return;
        generatedCode.setText(verifier.getReviewCode());
    }
}
