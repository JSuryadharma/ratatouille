package com.example.ratatouille.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ratatouille.db.DatabaseVars.UsersTable.*;

public class DatabaseHelper {

    static FirebaseDatabase db = FirebaseDatabase.getInstance();
    static FirebaseAuth dbAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase getDb() {
        return db;
    }

    public static FirebaseAuth getDbAuth() { return dbAuth; }
}
