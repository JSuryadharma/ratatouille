package com.example.ratatouille;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.controllers.VoucherController;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        customType(MainActivity.this, "fadein-to-fadeout");

        Intent startAppUI = new Intent(MainActivity.this, SplashScreen.class);
        startActivity(startAppUI);

        // Refresh Review Code Database
        ReviewController.refreshReviewVerificationDatabase();

        // user table test, firebase
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UsersTable.USERS_TABLE);

        ArrayList<Users> userlist = new ArrayList<Users>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                System.out.println("CEK:");
                for(DataSnapshot eachData : snapshot.getChildren()) {  //checks each data in snapshot's children
                    if (eachData.child("username").getValue().equals("jonathan090201")) {
                        Users currentUser = eachData.getValue(Users.class);
                        userlist.add(currentUser);
                    }
                }

                System.out.println("Found users:");
                for(Users u : userlist){
                    System.out.println(u.getUsername() + " , " + u.getName());
                }
                System.out.println("=========== END OF FILE ==============");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press; Do nothing
        Toast.makeText(getBaseContext(), "Please use the back button inside of the application.", Toast.LENGTH_LONG);
    }
}