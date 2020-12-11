package com.example.ratatouille.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.loginScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class UserController {

    public static Users UserLogin(Context context, String username, String password) {
        FirebaseDatabase db = DatabaseHelper.getDb();
        DatabaseReference user = db.getReference().child("Users").child(username);
//        Query user = table.orderByChild(DatabaseVars.UsersTable.USERNAME).equalTo(username);
        System.out.println(user);
        System.out.println(Calendar.getInstance().getTimeInMillis());

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(Calendar.getInstance().getTimeInMillis());
                if(snapshot.exists()) {
                    String pass = snapshot.child("Password").getValue().toString();
                    System.out.println("PERBANDINGAN: " + pass + " " + password);
                    try {
                        if(pass.equals(password)) {
                            System.out.println("jancuk");
                            DataSnapshot ds = snapshot;

//                            VariablesUsed.loggedUser = ds.getValue(Users.class);

                            VariablesUsed.loggedUser = new Users(
                                    UUID.fromString(ds.child(DatabaseVars.UsersTable.USER_ID).getValue(String.class)),
                                    ds.child(DatabaseVars.UsersTable.EMAIL).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.USERNAME).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.NAME).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.PHONE).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.ADDRESS).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.LASTLOGIN).getValue(String.class)
                            );
                            System.out.println(VariablesUsed.loggedUser.getUser_id());
                        }
                    } catch (Exception e) {
                        Log.w(TAG, e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read.", error.toException());
            }
        });
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Fetching data from Database..");
        pd.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pd.dismiss();
        return VariablesUsed.loggedUser;
    }
}
