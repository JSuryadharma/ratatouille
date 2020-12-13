package com.example.ratatouille.db;

import android.content.Context;

import com.example.ratatouille.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    static FirebaseDatabase db = FirebaseDatabase.getInstance();
    static FirebaseAuth dbAuth = FirebaseAuth.getInstance();
    static GoogleSignInClient gsc;

    public static FirebaseDatabase getDb() {
        return db;
    }

    public static FirebaseAuth getDbAuth() { return dbAuth; }

    public static GoogleSignInClient getGsc(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(context, gso);
        return gsc;
    }
}
