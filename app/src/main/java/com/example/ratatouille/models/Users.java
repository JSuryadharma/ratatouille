package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class Users {

    private String user_id, email, username, name, phone, address, last_login;

    public Users(String user_id, String email, String username, String name, String phone, String address, String last_login) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.last_login = last_login;
    }

    public static void save(String username, String email, String password, String name, String phone, String address){
        FirebaseAuth dAuth = DatabaseHelper.getDbAuth();

        dAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = dAuth.getCurrentUser();

                    DatabaseVars.UsersTable dbVars = new DatabaseVars.UsersTable();
                    DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(dbVars.USERS_TABLE).child(user.getUid());

                    dbRef.child(dbVars.USERNAME).setValue(username);
                    dbRef.child(dbVars.EMAIL).setValue(email);
                    dbRef.child(dbVars.NAME).setValue(name);
                    dbRef.child(dbVars.PHONE).setValue(phone);
                    dbRef.child(dbVars.ADDRESS).setValue(address);
                    dbRef.child(dbVars.LASTLOGIN).setValue(new Timestamp(System.currentTimeMillis()));

                    user.sendEmailVerification();

                    Log.w(TAG, "Sign Up has been completed! Please check your email to Log In!");

                } else {
                    Log.w(TAG, "Failed to save user data!");
                }
            }
        });
    }

    public static void updateProfile(String username, String email, String password, String name, String phone, String address){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(DatabaseVars.UsersTable.USERS_TABLE);
        dbRef.child(DatabaseVars.UsersTable.USERNAME).setValue(username);
        dbRef.child(DatabaseVars.UsersTable.PASSWORD).setValue(password);
        dbRef.child(DatabaseVars.UsersTable.NAME).setValue(name);
        dbRef.child(DatabaseVars.UsersTable.PHONE).setValue(phone);
        dbRef.child(DatabaseVars.UsersTable.ADDRESS).setValue(address);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

}
