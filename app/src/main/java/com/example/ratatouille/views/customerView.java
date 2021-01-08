package com.example.ratatouille.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ratatouille.R;
import com.example.ratatouille.customerFavourite;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class customerView extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragmentProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().hide();

        Toast.makeText(customerView.this, "Welcome back!" + VariablesUsed.currentUser.getUsername(), Toast.LENGTH_LONG);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new customerHomeFragment();

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            }
        );
    }
}
