package com.example.ratatouille.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.Utils.Utils;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.loginScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    public static void UserLogin(Context context, String email, String password) {
        // We use Firebase Auth, for authentication process..
        FirebaseAuth dbAuth = DatabaseHelper.getDbAuth();

        dbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            VariablesUsed.loggedUser = dbAuth.getCurrentUser();
                            if(VariablesUsed.loggedUser.isEmailVerified()) {
                                Utils.showSuccessMessage(context, "Success Logged In", "Welcome, " + email + " !");
                            } else {
                                Utils.showAlertMessage(context, "Please Verify Account", "To continue, please check verification link on your email account!");
                            }
                        } else {
                            Utils.showAlertMessage(context, "Incorrect Credentials","Please try again, or contact our Customer Service for help.");
                        }
                    }
                });

        if(VariablesUsed.loggedUser != null) {
            DatabaseVars.UsersTable dbVars = new DatabaseVars.UsersTable();
            DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(dbVars.USERS_TABLE).child(VariablesUsed.loggedUser.getUid());

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users currentUser = new Users(
                            UUID.fromString(VariablesUsed.loggedUser.getUid()),
                            snapshot.child(dbVars.EMAIL).getValue().toString(),
                            snapshot.child(dbVars.USERNAME).getValue().toString(),
                            snapshot.child(dbVars.NAME).getValue().toString(),
                            snapshot.child(dbVars.PHONE).getValue().toString(),
                            snapshot.child(dbVars.ADDRESS).getValue().toString(),
                            snapshot.child(dbVars.LASTLOGIN).getValue().toString()
                    );
                    VariablesUsed.currentUser = currentUser; // update the current logged in user..
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "User Data retrieval failed!");
                }
            });
        }
    }

    public static void UserSignup(Context context, String username, String email, String password, String name, String phone, String address){
        Users.save(username, email, password, name, phone, address);
    }
}
