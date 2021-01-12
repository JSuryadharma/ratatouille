package com.example.ratatouille.views;

import android.content.Intent;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.R;
import com.example.ratatouille.db.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Tag;

public class forgotPasswordActivity extends AppCompatActivity {
    LinearLayout backButton;
    EditText fpInput;
    LinearLayout submitButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgotpassword);

        backButton = findViewById(R.id.fp_backbutton);
        fpInput = findViewById(R.id.fp_input);
        submitButton = findViewById(R.id.fp_button);

        fpInput.setText("Enter your email");

        fpInput.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fpInput.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(forgotPasswordActivity.this, loginScreen.class);
                startActivity(backIntent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Processing..", Toast.LENGTH_LONG).show();
                DatabaseHelper.getDbAuth().sendPasswordResetEmail(fpInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Email sent.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Invalid Request! Please Try Again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }


}
