package com.example.ratatouille.views;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.voucherRecyclerAdapter;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.ratatouille.vars.VariablesUsed.currentVoucher;
import static com.example.ratatouille.vars.VariablesUsed.firstLoginBoolean;
import static maes.tech.intentanim.CustomIntent.customType;

public class customerView extends AppCompatActivity {
    public static RelativeLayout menubar_layout;
    public static RelativeLayout currentVoucher_area;
    public static TextView currentVoucher_name;
    private ImageView navbar_home;
    private ImageView navbar_profile;
    private ImageView navbar_favourite;
    private ImageView navbar_home_i;
    private ImageView navbar_profile_i;
    private ImageView navbar_favourite_i;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer);
        customType(customerView.this, "fadein-to-fadeout");

        // Set the default view in fragment
        selectedFragment = new customerHomeFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, selectedFragment).commit();

        if(firstLoginBoolean == false) {
            Utils.showDialogMessage(R.drawable.verified_logo, this, "Success Log In", "Welcome back, " + VariablesUsed.currentUser.getName() + " !");
            firstLoginBoolean = true;
        }

        navbar_home = findViewById(R.id.navbar_home);
        navbar_profile = findViewById(R.id.navbar_profile);
        navbar_favourite = findViewById(R.id.navbar_favourite);

        navbar_home_i = findViewById(R.id.navbar_home_i);
        navbar_profile_i = findViewById(R.id.navbar_profile_i);
        navbar_favourite_i = findViewById(R.id.navbar_favourite_i);

        currentVoucher_area = findViewById(R.id.currentVoucher_area);
        currentVoucher_name = findViewById(R.id.currentVoucher_name);
        menubar_layout = findViewById(R.id.menubar_layout);

        navbar_home_i.setVisibility(View.VISIBLE);
        navbar_profile_i.setVisibility(View.INVISIBLE);
        navbar_favourite_i.setVisibility(View.INVISIBLE);

        if(currentVoucher == null) {
            Animation anim = AnimationUtils.loadAnimation(customerView.this, R.anim.bottom_to_up);
            currentVoucher_area.startAnimation(anim);
            currentVoucher_area.setVisibility(View.GONE);
            currentVoucher_name.setText("");
        } else {
            Animation anim = AnimationUtils.loadAnimation(customerView.this, R.anim.up_to_bottom);
            currentVoucher_area.setVisibility(View.VISIBLE);
            currentVoucher_area.startAnimation(anim);
            currentVoucher_name.setText(currentVoucher.getVoucherName() + " " + currentVoucher.getVoucherDisc().toString() + "%");
        }
        menubar_layout.setVisibility(View.VISIBLE);

        navbar_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navbar_home_i.setVisibility(View.VISIBLE);
                navbar_profile_i.setVisibility(View.INVISIBLE);
                navbar_favourite_i.setVisibility(View.INVISIBLE);

                selectedFragment = new customerHomeFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        navbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navbar_home_i.setVisibility(View.INVISIBLE);
                navbar_profile_i.setVisibility(View.VISIBLE);
                navbar_favourite_i.setVisibility(View.INVISIBLE);

                selectedFragment = new ViewProfileFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        navbar_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navbar_home_i.setVisibility(View.INVISIBLE);
                navbar_profile_i.setVisibility(View.INVISIBLE);
                navbar_favourite_i.setVisibility(View.VISIBLE);

                selectedFragment = new customerFavourite();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, selectedFragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG).show();
    }

}
