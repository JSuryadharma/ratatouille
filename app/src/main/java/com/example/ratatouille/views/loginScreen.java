package com.example.ratatouille.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.SplashScreen;
import com.example.ratatouille.Utils.Utils;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;

public class loginScreen extends AppCompatActivity {
    EditText userTextbox, passTextbox;
    LinearLayout btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        userTextbox = findViewById(R.id.userTextbox);
        passTextbox = findViewById(R.id.passTextbox);
        btSignIn = findViewById(R.id.loginButton);

        btSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println(userTextbox.getText().toString());
                String usernameInput = userTextbox.getText().toString();
                String passwordInput = passTextbox.getText().toString();
                if(Utils.validateUsername(usernameInput) && Utils.validatePassword(passwordInput)) {

                    Users currentUser = UserController.UserLogin(loginScreen.this, usernameInput, passwordInput);
                    if(currentUser != null){
                        Utils.showSuccessMessage(loginScreen.this, "Success Logged In", "Welcome, " + currentUser.getName() + " !");
                    } else {
                        Utils.showAlertMessage(loginScreen.this, "User not found!","Please try again, or contact our Customer Service for help.");
                    }
                }
                else {
                    if(!Utils.validateUsername(userTextbox.getText().toString())) {
                        userTextbox.setError("Input is invalid!");
                    }
                    if(!Utils.validatePassword(userTextbox.getText().toString())) {
                        passTextbox.setError("Input is invalid!");
                    }
                }
            }
        });

    }
}
