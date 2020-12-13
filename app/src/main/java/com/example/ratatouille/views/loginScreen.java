package com.example.ratatouille.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.controllers.UserController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class loginScreen extends AppCompatActivity {
    EditText userTextbox, passTextbox;
    TextView showButton;
    LinearLayout btSignIn;
    TextView dhaButton, loginWithButton;

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
        loginWithButton = findViewById(R.id.li_signInWithButton);

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

        loginWithButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent googleSignInIntent = DatabaseHelper.getGsc(loginScreen.this).getSignInIntent();
                startActivityForResult(googleSignInIntent, DatabaseVars.RC_SIGN_IN);
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
                UserController.firebaseAuthWithGoogle(loginScreen.this, account.getIdToken());
            } catch (ApiException e) {
                Utils.showAlertMessage(loginScreen.this, "Login with Google Failed","Please try again later, or contact our Customer Service for help.");
            }
        }
    }

}
