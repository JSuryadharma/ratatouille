package com.example.ratatouille.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ratatouille.R;
import com.example.ratatouille.utils.introductionAdapter;

public class introductionView extends AppCompatActivity {

    private LinearLayout introButton;
    private ViewPager intro_viewPager;
    private introductionAdapter introAdapter;
    private SeekBar seekBar;
    private PagerAdapter pageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introduction);

        //Initiate viewPager
        intro_viewPager = findViewById(R.id.intro_viewPager);
        seekBar = findViewById(R.id.seekBar);

        loadCards();

        intro_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                seekBar.setProgress(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        introButton = findViewById(R.id.introButton);

        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerIntent = new Intent(introductionView.this, customerView.class);
                startActivity(customerIntent);
                finish();
            }
        });
    }

    private void loadCards() {
        //set up adapter, will automatically call the instantiateItem
        introAdapter = new introductionAdapter(this);
        //set adapter to view Pager
        intro_viewPager.setAdapter(introAdapter);
    }
}
