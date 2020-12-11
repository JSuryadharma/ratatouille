package com.example.ratatouille.models;

import android.database.Cursor;
import android.util.Log;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static com.example.ratatouille.db.DatabaseVars.UsersTable.*;

public class Users {

    private UUID user_id;
    private String email, username, name, phone, address, last_login;

    public Users(){

    }

    public Users(UUID user_id, String email, String username, String name, String phone, String address, String last_login) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.last_login = last_login;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
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
