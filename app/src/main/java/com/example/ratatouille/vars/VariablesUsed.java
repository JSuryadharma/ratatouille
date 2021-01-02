package com.example.ratatouille.vars;

import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;
import com.google.firebase.auth.FirebaseUser;

public class VariablesUsed { //currently active global variables..
    public static FirebaseUser loggedUser = null; // this is for authetication (email and password for login)..
    public static Users currentUser = null; // this is for userdatas information (else than email and password)..
    public static Restaurant currentRestaurant = null;
//    public static Employee currentEmployee = null;
}
