package com.example.ratatouille;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.ratatouille.views.loginScreen;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginScreenIntent = new Intent(SplashScreen.this, loginScreen.class);
                startActivity(loginScreenIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
