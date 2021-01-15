package com.example.ratatouille.views;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static maes.tech.intentanim.CustomIntent.customType;

public class customerView extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer);
        customType(customerView.this, "fadein-to-fadeout");

        // Set the default view in fragment
        selectedFragment = new customerHomeFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, selectedFragment).commit();

//        Toast.makeText(getBaseContext(), "Welcome back, " + VariablesUsed.currentUser.getName() + " !", Toast.LENGTH_LONG).show();

        Utils.showDialogMessage(R.drawable.verified_logo, this, "Success Log In", "Welcome back! " + VariablesUsed.currentUser.getName());

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId() == R.id.nav_profile) {
                        selectedFragment = new ViewProfileFragment();
                    }

                    if (item.getItemId() == R.id.nav_home) {
                        selectedFragment = new customerHomeFragment();
                    }

                    if (item.getItemId() == R.id.nav_favourites) {
                        selectedFragment = new customerFavourite();
                    }

                    if(selectedFragment != null) {
                        MediaPlayer player = MediaPlayer.create(customerView.this, R.raw.personleave);
                        player.start();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            }
        );
    }


}
