package com.example.ratatouille;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille.connector.ConnectorVars;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.helper.HashHelper;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.vars.VariablesUsed;

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
                if(userTextbox.getText().toString() != null && passTextbox.getText().toString() != null && userTextbox.getText().toString() != "" && passTextbox.getText().toString() != "") {
                    String username = userTextbox.getText().toString().trim();
                    String password = HashHelper.sha256(passTextbox.getText().toString().trim());
                    VariablesUsed.loggedUser = Users.UserLogin(username, password);
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
            }
        });

        ConnectorVars.dbh = new DatabaseHelper(this);
    }
}