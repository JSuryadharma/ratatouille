package com.example.ratatouille.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.R;
import com.example.ratatouille.Utils.Utils;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.auth.FirebaseUser;

public class signupScreen extends AppCompatActivity {
    TextView usernameField, emailField, nameField, passwordField, phoneField, addressField;
    LinearLayout signUpButton;
    TextView backButton;
    TextView showButton;

    @Override
    protected void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        usernameField = findViewById(R.id.su_username);
        emailField = findViewById(R.id.su_email);
        nameField = findViewById(R.id.su_name);
        passwordField = findViewById(R.id.su_password);
        phoneField = findViewById(R.id.su_phone);
        addressField = findViewById(R.id.su_address);

        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance()); // set model password pertama (hidden)..

        signUpButton = findViewById(R.id.su_signUpButton);
        backButton = findViewById(R.id.su_backButton);
        showButton = findViewById(R.id.su_backButton);

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
                String usernameInput = usernameField.getText().toString();
                String emailInput = emailField.getText().toString();
                String nameInput = nameField.getText().toString();
                String passwordInput = passwordField.getText().toString();
                String phoneInput = phoneField.getText().toString();
                String addressInput = addressField.getText().toString();

                if (Utils.validateEmail(emailInput) && Utils.validateUsername(usernameInput) && Utils.validatePassword(passwordInput) && Utils.validatePhone(phoneInput)) {
                    UserController.UserSignup(signupScreen.this, usernameInput, emailInput, passwordInput, nameInput, phoneInput, addressInput);

                    Intent loginIntent = new Intent(signupScreen.this, loginScreen.class);

                    AlertDialog.Builder signupAlert = new AlertDialog.Builder(signupScreen.this) // alert message after sign up..
                            .setTitle("Sign Up Attention")
                            .setMessage("Your account has been successfully saved into our database, however you need to verify your account first.")
                            .setIcon(R.drawable.ic_warning)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    startActivity(loginIntent);
                                }
                            });

                        signupAlert.show();

                } else {
                    if(!Utils.validateEmail(emailInput)){
                        emailField.setError("Invalid Input!");
                    }
                    if(!Utils.validateUsername(usernameInput)){
                        usernameField.setError("Invalid Input!");
                    }
                    if(!Utils.validatePassword(passwordInput)){
                        passwordField.setError("Invalid Input!");
                    }
                    if(!Utils.validatePhone(phoneInput)){
                        phoneField.setError("Invalid Input!");
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(signupScreen.this, loginScreen.class);
                startActivity(loginIntent);
            }
        });
    }
}
