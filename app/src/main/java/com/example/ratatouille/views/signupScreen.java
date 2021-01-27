package com.example.ratatouille.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.controllers.UserController;

import static maes.tech.intentanim.CustomIntent.customType;

public class signupScreen extends AppCompatActivity {
    TextView usernameField, emailField, nameField, passwordField, phoneField, addressField;
    LinearLayout signUpButton;
    TextView backButton;
    TextView showButton;

    @Override
    protected void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        customType(signupScreen.this, "fadein-to-fadeout");

        usernameField = findViewById(R.id.su_username);
        emailField = findViewById(R.id.su_email);
        nameField = findViewById(R.id.su_name);
        passwordField = findViewById(R.id.su_password);
        phoneField = findViewById(R.id.su_phone);
        addressField = findViewById(R.id.su_address);

        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance()); // set model password pertama (hidden)..

        signUpButton = findViewById(R.id.su_signUpButton);
        backButton = findViewById(R.id.su_backButton);
        showButton = findViewById(R.id.su_showButton);

        signUpButton.setBackgroundResource(R.drawable.round_button);

        showButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(passwordField.getTransformationMethod() != HideReturnsTransformationMethod.getInstance()) {
                    passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showButton.setTextColor(Color.BLUE);
                } else {
                    passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showButton.setTextColor(Color.BLACK);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpButton.setBackgroundResource(R.drawable.pressed_round_button);
                String usernameInput = usernameField.getText().toString();
                String emailInput = emailField.getText().toString();
                String nameInput = nameField.getText().toString();
                String passwordInput = passwordField.getText().toString();
                String phoneInput = phoneField.getText().toString();
                String addressInput = addressField.getText().toString();

                if (Utils.validateInput(emailInput) && Utils.validateInput(usernameInput) && Utils.validateInput(passwordInput) && Utils.validateInput(phoneInput) &&
                        Utils.validateEmail(emailInput) && Utils.validateUsername(usernameInput) && Utils.validatePassword(passwordInput) && Utils.validatePhone(phoneInput)) {
                    UserController.UserSignup(signupScreen.this, emailInput, usernameInput, passwordInput, nameInput, phoneInput, addressInput);

                    Toast.makeText(getBaseContext(), "Account Created Successfully!", Toast.LENGTH_LONG).show();

                    Utils.actresponse dialogRsp = new Utils.actresponse() {
                        @Override
                        public void okResponse() {
                            Intent loginIntent = new Intent(signupScreen.this, loginScreen.class);
                            startActivity(loginIntent);
                        }
                    };

                    Utils.showActionMessage(R.drawable.ic_warning, dialogRsp, signupScreen.this, "Sign Up Attention", "Your account has been successfully saved into our database,\n however you need to verify your account first.");

                } else {
                    if(!Utils.validateInput(emailInput) || !Utils.validateEmail(emailInput)){
                        emailField.setError("Invalid Input!");
                    }
                    if(!Utils.validateInput(usernameInput) || !Utils.validateUsername(usernameInput)){
                        usernameField.setError("Invalid Input!");
                    }
                    if(!Utils.validateInput(passwordInput) || !Utils.validatePassword(passwordInput)){
                        passwordField.setError("Invalid Input!");
                    }
                    if(!Utils.validateInput(phoneInput) || !Utils.validatePhone(phoneInput)){
                        phoneField.setError("Invalid Input!");
                    }
                }
            }
        });

        backButton.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButton.setTextColor(Color.GRAY);
                Intent loginIntent = new Intent(signupScreen.this, loginScreen.class);
                MediaPlayer player = MediaPlayer.create(signupScreen.this, R.raw.personleave);
                player.start();
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }
}
