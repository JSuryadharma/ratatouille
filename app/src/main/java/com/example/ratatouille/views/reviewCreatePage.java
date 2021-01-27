package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.controllers.restoDetailController;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;

public class reviewCreatePage extends AppCompatActivity {
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
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        backButton = findViewById(R.id.reviewcreate_backButton);
        backButton_text = findViewById(R.id.reviewcreate_backButton_text);
        submitButton = findViewById(R.id.reviewcreate_submitButton);
        maskRate_num = findViewById(R.id.reviewcreate_maskrate_num);
        tempRate_num = findViewById(R.id.reviewcreate_tempscan_num);
        sanitizeRate_num = findViewById(R.id.reviewcreate_sanitation_num);
        socialDistancingRate_num = findViewById(R.id.reviewcreate_socialdistance_num);
        physicalBarriers_num = findViewById(R.id.reviewcreate_physical_num);
        reviewMessage = findViewById(R.id.reviewcreate_message);
        context = this;

        //value controllers
        maskRate_min = findViewById(R.id.reviewcreate_maskrate_min);
        maskRate_add = findViewById(R.id.reviewcreate_maskrate_add);
        tempRate_min = findViewById(R.id.reviewcreate_tempscan_min);
        tempRate_add = findViewById(R.id.reviewcreate_tempscan_add);
        sanitizeRate_min = findViewById(R.id.reviewcreate_sanitation_min);
        sanitizeRate_add = findViewById(R.id.reviewcreate_sanitation_add);
        socialDistancingRate_min = findViewById(R.id.reviewcreate_socialdistance_min);
        socialDistancingRate_add = findViewById(R.id.reviewcreate_socialdistance_add);
        physicalBarriers_min = findViewById(R.id.reviewcreate_physical_min);
        physicalBarriers_add = findViewById(R.id.reviewcreate_physical_add);

        backButton_text.setTextColor(Color.BLACK);

        currentRestaurantID = VariablesUsed.currentRestoDetail.getResto_id();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                Intent intent = new Intent(context, restaurantDetails.class);
                restoDetailController.query(context, intent, currentRestaurantID);
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
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unable to proceed.", "Rates can't be valued 0 or null.");
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
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unable to proceed.", "Rates can't be valued 0 or null.");
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
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unable to proceed.", "Rates can't be valued 0 or null.");
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
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unable to proceed.", "Rates can't be valued 0 or null.");
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
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unable to proceed.", "Rates can't be valued 0 or null.");
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

        Utils.actresponse rsp = new Utils.actresponse(){
            @Override
            public void okResponse() {
                VariablesUsed.currentUser.setPoints(VariablesUsed.currentUser.getPoints() + 500);
                Intent intent = new Intent(context, restaurantDetails.class);
                restoDetailController.query(context, intent, currentRestaurantID);
            }
        };

        submitButton.setTextColor(Color.BLACK);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(context, R.raw.open);
                player.start();
                ReviewController.addReview(currentRestaurantID, currentMaskRate.doubleValue(), currentTempRate.doubleValue(), currentSanitizeRate.doubleValue(), currentSocialDistRate.doubleValue(), currentPhysicalRate.doubleValue(), reviewMessage.getText().toString());
                Utils.showActionMessage(R.drawable.verified_logo, rsp, context, "Thank You For Your Review!", "Here are your bonus +1000 points!");
            }
        });
    }
}
