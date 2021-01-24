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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.models.ReviewVerification;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.UUID;

public class ReviewCode extends AppCompatActivity {
    private String currentRestaurantID = "";
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView nextButton;
    private EditText inputReviewCode;
    private Context context;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            validAndVerified();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_code);

        backButton = findViewById(R.id.reviewcode_backButton);
        backButton_text = findViewById(R.id.reviewcode_backButton_text);
        nextButton = findViewById(R.id.reviewcode_nextButton);
        inputReviewCode = findViewById(R.id.reviewcode_code);
        context = this;

        String reviewCode = VariablesUsed.loggedUser.getUid()+"-"+VariablesUsed.currentRestoDetail.getResto_id();
        ReviewVerification rv = new ReviewVerification(reviewCode, VariablesUsed.currentRestoDetail.getResto_id(), VariablesUsed.loggedUser.getUid());
        rv.save();
        currentRestaurantID = rv.getRestaurantID();

        backButton_text.setTextColor(Color.BLACK);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
                player.start();
                ReviewController.deleteVerificationCode(reviewCode);
                finish();
            }
        });

        nextButton.setTextColor(Color.BLACK);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputReviewCode == null || inputReviewCode.getText().toString().equals("")){
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Unauthorized Action", "Please fill in your RVC Code!");
                    return;
                }
                nextButton.setTextColor(Color.DKGRAY);
                MediaPlayer player = MediaPlayer.create(context, R.raw.open);
                player.start();
                ReviewController.checkReviewVerification(context, cb, inputReviewCode.getText().toString(), currentRestaurantID);
            }
        });
        inputReviewCode.setText(reviewCode);
    }

    public void validAndVerified() {
        Intent intent = new Intent(context, reviewCreatePage.class);
        startActivity(intent);
        finish();
    }
}
