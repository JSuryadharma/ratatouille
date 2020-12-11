package com.example.ratatouille;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.helper.HashHelper;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    EditText userTextbox, passTextbox;
    Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userTextbox = findViewById(R.id.userTextbox);
        passTextbox = findViewById(R.id.passTextbox);

        btSignIn = findViewById(R.id.signinButton);

        btSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println(userTextbox.getText().toString());
                if(userTextbox.getText().toString() != null && passTextbox.getText().toString() != null && !userTextbox.getText().toString().equals("") && !passTextbox.getText().toString().equals("")) {
                    String username = userTextbox.getText().toString().trim();
                    String password = HashHelper.sha256(passTextbox.getText().toString().trim());
                    UserLogin(username, password);
                    if(VariablesUsed.loggedUser != null){
                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                MainActivity.this
                        );
                        alert.setIcon(R.drawable.verified_logo);
                        alert.setTitle("Logged In Successfully!!");
                        alert.setMessage("Welcome, " + userTextbox.getText().toString());
                        alert.show();
                    }
                }
                else {
                    if(userTextbox.getText().toString().equals("")) {
                        userTextbox.setError("Username Cannot be Empty!");
                    }
                    if(passTextbox.getText().toString().equals("")) {
                        passTextbox.setError("Password Cannot be Empty!");
                    }
                }
            }
        });

    }

    protected void UserLogin(String username, String password) {

        FirebaseDatabase db = DatabaseHelper.getDb();
        DatabaseReference table = db.getReference(DatabaseVars.UsersTable.USERS_TABLE);
        Query user = table.orderByChild(DatabaseVars.UsersTable.USERNAME).equalTo(username);
        System.out.println(user);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String pass = snapshot.child(username).child(DatabaseVars.UsersTable.PASSWORD).getValue(String.class);

                    try {
                        if(pass.equals(password)) {

                            DataSnapshot ds = snapshot.child(username);

                            VariablesUsed.loggedUser = new Users(
                                    UUID.fromString(ds.child(DatabaseVars.UsersTable.USER_ID).getValue(String.class)),
                                    ds.child(DatabaseVars.UsersTable.EMAIL).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.USERNAME).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.NAME).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.PHONE).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.ADDRESS).getValue(String.class),
                                    ds.child(DatabaseVars.UsersTable.LASTLOGIN).getValue(String.class)
                            );
                            //TODO pergi ke menu awal
                        }
                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    MainActivity.this
                            );
                            alert.setTitle("Error");
                            alert.setMessage("User Not Exists or Wrong Password!");
                            alert.show();
                        }
                    } catch (Exception e) {
                        Log.w(TAG, e.getMessage(), e);
                    }
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            MainActivity.this
                    );
                    alert.setTitle("Error");
                    alert.setMessage("User Not Exists or Wrong Password!");
                    alert.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read.", error.toException());
            }
        });
    }

}