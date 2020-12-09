package com.example.ratatouille;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText userTextbox, passTextbox;
    Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTextbox = findViewById(R.id.userTextbox);
        passTextbox = findViewById(R.id.passTextbox);

        btSignIn = findViewById(R.id.signinButton);

        btSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println(userTextbox.getText().toString());
                if(userTextbox.getText().toString().equals("josur") &&
                passTextbox.getText().toString().equals("admin")){
                    System.out.println("masuk");
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            MainActivity.this
                    );
                    alert.setIcon(R.drawable.verified_logo);
                    alert.setTitle("Logged In Successfully!!");
                    alert.setMessage("Welcome, " + userTextbox.getText().toString());
                    alert.show();
                }
            }
        });

    }
}