package com.example.ratatouille.vars;

import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.models.restoDetailModel;
import com.google.firebase.auth.FirebaseUser;

public class VariablesUsed { //currently active global variables..
    public static FirebaseUser loggedUser = null; // this is for authetication (email and password for login)..
    public static Users currentUser = null; // this is for userdatas information (else than email and password)..
    public static Restaurant currentRestaurant = null;
    public static Vouchers currentVoucher = null;
    public static String currentLocation = null;
    public static String API_KEY = "559a657e19a9ac2859c445071363cb01";
    public static String API_BASE_URL = "https://developers.zomato.com/api/v2.1/";
    public static String DEFAULT_PHOTO = "https://i.imgur.com/YCuMtJi.png";
    public static restoDetailModel currentRestoDetail = null;
//    public static Employee currentEmployee = null;
}
