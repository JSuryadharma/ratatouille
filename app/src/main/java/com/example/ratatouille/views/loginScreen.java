package com.example.ratatouille.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.R;
import com.example.ratatouille.Utils.Utils;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.resources.TextAppearanceConfig;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginScreen extends AppCompatActivity {
    EditText userTextbox, passTextbox;
    TextView showButton;
    LinearLayout btSignIn;
    TextView dhaButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        userTextbox = findViewById(R.id.li_userTextbox);
        passTextbox = findViewById(R.id.li_passTextbox);
        btSignIn = findViewById(R.id.li_loginButton);
        showButton = findViewById(R.id.li_showButton);
        dhaButton = findViewById(R.id.li_dhaButton);

        passTextbox.setTransformationMethod(PasswordTransformationMethod.getInstance()); // set model password pertama (hidden)..

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
                System.out.println(userTextbox.getText().toString());
                String emailInput = userTextbox.getText().toString();
                String passwordInput = passTextbox.getText().toString();

                if(Utils.validateEmail(emailInput) && Utils.validatePassword(passwordInput)) {
                    UserController.UserLogin(loginScreen.this, emailInput, passwordInput);
                }
                else {
                    if(!Utils.validateEmail(userTextbox.getText().toString())) {
                        userTextbox.setError("Invalid Input!");
                    }
                    if(!Utils.validatePassword(userTextbox.getText().toString())) {
                        passTextbox.setError("Invalid Input!");
                    }
                }
            }
        });

        dhaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(loginScreen.this, signupScreen.class);
                startActivity(signUpIntent);
            }
        });

    }
}
