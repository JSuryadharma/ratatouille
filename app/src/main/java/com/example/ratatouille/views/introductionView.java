package com.example.ratatouille.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ratatouille.R;
import com.r0adkll.slidr.Slidr;

public class introductionView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction1_recreate);
        getSupportActionBar().hide();
    }
}
