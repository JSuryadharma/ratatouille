package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.RestaurantController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static maes.tech.intentanim.CustomIntent.customType;

public class loginScreen extends AppCompatActivity {
    private EditText userTextbox, passTextbox;
    private TextView showButton;
    private LinearLayout btSignIn;
    private TextView welcomeLabel;
    private ImageView imageLogo;
    private TextView dhaButton, forgotPassword;
    private String mode = "customer";

    callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            if(u.getNumberOfLogins() > 1) {
                startMainMenu(context);
            } else {
                startIntroPage(context);
            }
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        customType(loginScreen.this, "fadein-to-fadeout");

        welcomeLabel = findViewById(R.id.li_welcomeLabel);
        imageLogo = findViewById(R.id.li_logo);

        userTextbox = findViewById(R.id.li_userTextbox);
        passTextbox = findViewById(R.id.li_passTextbox);
        btSignIn = findViewById(R.id.li_loginButton);
        showButton = findViewById(R.id.li_showButton);
        dhaButton = findViewById(R.id.li_dhaButton);
        forgotPassword = findViewById(R.id.li_forgotPassword);

        btSignIn.setBackgroundResource(R.drawable.round_button);
        dhaButton.setTextColor(Color.WHITE);
        forgotPassword.setTextColor(Color.WHITE);

        passTextbox.setTransformationMethod(PasswordTransformationMethod.getInstance()); // set model password pertama (hidden)..

        welcomeLabel.setText("Welcome Back");
        imageLogo.setImageResource(R.drawable.ratatouille_logor);

        imageLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(loginScreen.this, R.anim.fragment_fade_exit);
                imageLogo.startAnimation(anim);

                if(mode.equals("customer")) {
                    MediaPlayer player = MediaPlayer.create(loginScreen.this, R.raw.open);
                    player.start();

                    Utils.showDialogMessage(R.drawable.ic_warning, loginScreen.this, "Attention", "Please be aware that you're trying to access\n the reservation system that was meant for restaurants only!");

                    imageLogo.setImageResource(R.drawable.ic_restaurant);
                    welcomeLabel.setText("Reservations");
                    mode = "restaurant";
                    dhaButton.setVisibility(View.GONE);
                    forgotPassword.setVisibility(View.GONE);
                } else {
                    imageLogo.setImageResource(R.drawable.ratatouille_logor);
                    welcomeLabel.setText("Welcome Back");
                    mode = "customer";
                    dhaButton.setVisibility(View.VISIBLE);
                    forgotPassword.setVisibility(View.VISIBLE);
                }
            }
        });

        showButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(passTextbox.getTransformationMethod() != HideReturnsTransformationMethod.getInstance()) {
                    passTextbox.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showButton.setTextColor(Color.BLUE);
                } else {
                    passTextbox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showButton.setTextColor(Color.BLACK);
                }
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btSignIn.setBackgroundResource(R.drawable.pressed_round_button);
                String emailInput = userTextbox.getText().toString();
                String passwordInput = passTextbox.getText().toString();

                switch(mode){
                    case "customer":
                        if(Utils.validateInput(emailInput) && Utils.validateInput(passwordInput) && Utils.validateEmail(emailInput) && Utils.validatePassword(passwordInput)) {
                            UserController.UserLogin(cb, loginScreen.this, emailInput, passwordInput);
                        }
                        else {
                            if(!Utils.validateInput(emailInput) && !Utils.validateEmail(emailInput)) {
                                userTextbox.setError("Invalid Input!");
                            }
                            if(!Utils.validateInput(passwordInput) && !Utils.validatePassword(passwordInput)) {
                                passTextbox.setError("Invalid Input!");
                            }
                        }
                        break;
                    case "restaurant":
                        if(Utils.validateInput(emailInput) && Utils.validateInput(passwordInput) && Utils.validateEmail(emailInput) && Utils.validatePassword(passwordInput)) {
                            RestaurantController.RestaurantLogin(loginScreen.this, emailInput, passwordInput);
                        }
                        else {
                            if(!Utils.validateInput(emailInput) && !Utils.validateEmail(emailInput)) {
                                userTextbox.setError("Invalid Input!");
                            }
                            if(!Utils.validateInput(passwordInput) && !Utils.validatePassword(passwordInput)) {
                                passTextbox.setError("Invalid Input!");
                            }
                        }
                        break;
                }
            }
        });

        dhaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dhaButton.setTextColor(Color.BLUE);
                MediaPlayer player = MediaPlayer.create(loginScreen.this, R.raw.open);
                player.start();
                Intent signUpIntent = new Intent(loginScreen.this, signupScreen.class);
                startActivity(signUpIntent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                forgotPassword.setTextColor(Color.BLUE);
                MediaPlayer player = MediaPlayer.create(loginScreen.this, R.raw.open);
                player.start();
                Intent forgotIntent = new Intent(loginScreen.this, forgotPasswordActivity.class);
                startActivity(forgotIntent);
            }
        });

    }

    // Override khusus login google doang
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == DatabaseVars.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                UserController.firebaseAuthWithGoogle(cb, loginScreen.this, account.getIdToken());
            } catch (ApiException e) {
                Utils.showDialogMessage(R.drawable.ic_warning, loginScreen.this, "Login with Google Failed","Please try again later, or contact our Customer Service for help.");
            }
        }
    }

    public void startMainMenu(Context context) {
        Intent mainMenuIntent = new Intent(context, customerView.class);
        startActivity(mainMenuIntent);
    }

    public void startIntroPage(Context context){
        Intent introIntent = new Intent(context, introductionView.class);
        startActivity(introIntent);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }
}
