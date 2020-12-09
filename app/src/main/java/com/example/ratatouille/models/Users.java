package com.example.ratatouille.models;

import android.database.Cursor;
import android.util.Log;

import com.example.ratatouille.connector.ConnectorVars;

import java.util.UUID;

import static android.content.ContentValues.TAG;
import static com.example.ratatouille.db.DatabaseVars.UsersTable.*;

public class Users {

    private UUID user_id;
    private String email, username, name, phone, address, last_login;

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

    public static Users UserLogin(String username, String password) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                USERS_TABLE, USERNAME, username, PASSWORD, password);

        ConnectorVars.db = ConnectorVars.dbh.getReadableDatabase();
        Cursor c = ConnectorVars.db.rawQuery(query, null);

        try {
            if(c.moveToFirst()){
                Users u = new Users(
                        UUID.fromString(c.getString(c.getColumnIndex(USER_ID))),
                        c.getString(c.getColumnIndex(EMAIL)),
                        c.getString(c.getColumnIndex(USERNAME)),
                        c.getString(c.getColumnIndex(NAME)),
                        c.getString(c.getColumnIndex(PHONE)),
                        c.getString(c.getColumnIndex(ADDRESS)),
                        c.getString(c.getColumnIndex(LASTLOGIN))
                );
                return u;
            }
        }catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return null;
    }
}
