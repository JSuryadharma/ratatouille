package com.example.ratatouille.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.utils.introductionAdapter;

import static maes.tech.intentanim.CustomIntent.customType;

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
        customType(introductionView.this, "fadein-to-fadeout");

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
        introButton.setBackgroundResource(R.drawable.round_button);
        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introButton.setBackgroundResource(R.drawable.pressed_round_button);
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

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }
}
