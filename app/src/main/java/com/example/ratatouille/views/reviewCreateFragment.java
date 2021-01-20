package com.example.ratatouille.views;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;

public class reviewCreateFragment extends Fragment {
    private String currentRestaurantID = "";
    private static Integer currentMaskRate = 0;
    private static Integer currentTempRate = 0;
    private static Integer currentSanitizeRate = 0;
    private static Integer currentSocialDistRate = 0;
    private static Integer currentPhysicalRate = 0;
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView submitButton;
    private TextView maskRate_num;
    private TextView tempRate_num;
    private TextView sanitizeRate_num;
    private TextView socialDistancingRate_num;
    private TextView physicalBarriers_num;
    private ImageView maskRate_min;
    private ImageView maskRate_add;
    private ImageView tempRate_min;
    private ImageView tempRate_add;
    private ImageView sanitizeRate_min;
    private ImageView sanitizeRate_add;
    private ImageView socialDistancingRate_min;
    private ImageView socialDistancingRate_add;
    private ImageView physicalBarriers_min;
    private ImageView physicalBarriers_add;
    private EditText reviewMessage;

    public reviewCreateFragment(String currentRestaurantID) {
        this.currentRestaurantID = currentRestaurantID;
        currentMaskRate = 0;
        currentTempRate = 0;
        currentSanitizeRate = 0;
        currentSocialDistRate = 0;
        currentPhysicalRate = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = getView().findViewById(R.id.reviewcreate_backButton);
        backButton_text = getView().findViewById(R.id.reviewcreate_backButton_text);
        submitButton = getView().findViewById(R.id.reviewcreate_submitButton);
        maskRate_num = getView().findViewById(R.id.reviewcreate_maskrate_num);
        tempRate_num = getView().findViewById(R.id.reviewcreate_tempscan_num);
        sanitizeRate_num = getView().findViewById(R.id.reviewcreate_sanitation_num);
        socialDistancingRate_num = getView().findViewById(R.id.reviewcreate_socialdistance_num);
        physicalBarriers_num = getView().findViewById(R.id.reviewcreate_physical_num);
        reviewMessage = getView().findViewById(R.id.reviewcreate_message);

        //value controllers
        maskRate_min = getView().findViewById(R.id.reviewcreate_maskrate_min);
        maskRate_add = getView().findViewById(R.id.reviewcreate_maskrate_add);
        tempRate_min = getView().findViewById(R.id.reviewcreate_tempscan_min);
        tempRate_add = getView().findViewById(R.id.reviewcreate_tempscan_add);
        sanitizeRate_min = getView().findViewById(R.id.reviewcreate_sanitation_min);
        sanitizeRate_add = getView().findViewById(R.id.reviewcreate_sanitation_add);
        socialDistancingRate_min = getView().findViewById(R.id.reviewcreate_socialdistance_min);
        socialDistancingRate_add = getView().findViewById(R.id.reviewcreate_socialdistance_add);
        physicalBarriers_min = getView().findViewById(R.id.reviewcreate_physical_min);
        physicalBarriers_add = getView().findViewById(R.id.reviewcreate_physical_add);

        backButton_text.setTextColor(Color.BLACK);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                Fragment backFragment = new reviewFragment(currentRestaurantID);
//                TODO: NANTI KALAU SUDAH JADI ACTIVITY DETAILS, JANGAN LUPA INI DIKERJAIN..
//                getParentFragmentManager().beginTransaction().replace()
            }
        });

        maskRate_num.setText(currentMaskRate.toString() + " / 5");
        tempRate_num.setText(currentTempRate.toString() + " / 5");
        sanitizeRate_num.setText(currentSanitizeRate.toString() + " / 5");
        socialDistancingRate_num.setText(currentSocialDistRate.toString() + " / 5");
        physicalBarriers_num.setText(currentPhysicalRate.toString() + " / 5");

        // Mask Rate
        maskRate_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMaskRate > 1){
                    currentMaskRate -= 1;
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Unable to proceed.", "Rates can't be valued 0 or null.");
                }
                maskRate_num.setText(currentMaskRate.toString() + " / 5");
            }
        });
        maskRate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMaskRate < 5){
                    currentMaskRate += 1;
                }
                maskRate_num.setText(currentMaskRate.toString() + " / 5");
            }
        });

        // Temp Rate
        tempRate_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTempRate > 1){
                    currentTempRate -= 1;
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Unable to proceed.", "Rates can't be valued 0 or null.");
                }
                tempRate_num.setText(currentTempRate.toString() + " / 5");
            }
        });
        tempRate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTempRate < 5){
                    currentTempRate += 1;
                }
                tempRate_num.setText(currentTempRate.toString() + " / 5");
            }
        });

        // Sanitize Rate
        sanitizeRate_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSanitizeRate > 1){
                    currentSanitizeRate -= 1;
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Unable to proceed.", "Rates can't be valued 0 or null.");
                }
                sanitizeRate_num.setText(currentSanitizeRate.toString() + " / 5");
            }
        });
        sanitizeRate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSanitizeRate < 5){
                    currentSanitizeRate += 1;
                }
                sanitizeRate_num.setText(currentSanitizeRate.toString() + " / 5");
            }
        });

        // Social Distancing Rate
        socialDistancingRate_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSocialDistRate > 1){
                    currentSocialDistRate -= 1;
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Unable to proceed.", "Rates can't be valued 0 or null.");
                }
                socialDistancingRate_num.setText(currentSocialDistRate.toString() + " / 5");
            }
        });
        socialDistancingRate_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSocialDistRate < 5){
                    currentSocialDistRate += 1;
                }
                socialDistancingRate_num.setText(currentSocialDistRate.toString() + " / 5");
            }
        });

        // Physical Barriers Rate
        physicalBarriers_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPhysicalRate > 1){
                    currentPhysicalRate -= 1;
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Unable to proceed.", "Rates can't be valued 0 or null.");
                }
                physicalBarriers_num.setText(currentPhysicalRate.toString() + " / 5");
            }
        });
        physicalBarriers_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPhysicalRate < 5){
                    currentPhysicalRate += 1;
                }
                physicalBarriers_num.setText(currentPhysicalRate.toString() + " / 5");
            }
        });

        submitButton.setTextColor(Color.BLACK);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                ReviewController.addReview(currentRestaurantID, currentMaskRate.doubleValue(), currentTempRate.doubleValue(), currentSanitizeRate.doubleValue(), currentSocialDistRate.doubleValue(), currentPhysicalRate.doubleValue(), reviewMessage.getText().toString());
                Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Thank You For Your Review!", "Here are your bonus +1000 points!");
                VariablesUsed.currentUser.setPoints(VariablesUsed.currentUser.getPoints() + 1000);
            }
        });
    }
}
