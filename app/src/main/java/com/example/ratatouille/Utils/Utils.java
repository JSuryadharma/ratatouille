package com.example.ratatouille.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

import com.example.ratatouille.MainActivity;
import com.example.ratatouille.R;

public class Utils {
    public static Boolean validateInput(String input) {
        if(input == null || input.equals("")){
            return false;
        }
        return true; //valid
    }
    public static Boolean validateEmail(String input){
        if(validateInput(input) == false || input.length() < 5){
            return false;
        }
        String[] verifiedEmailHost = {"@gmail.com", "@hotmail.com", "@yahoo.com", "@icloud.com", "@outlook.com"};

        for(int i=0; i<verifiedEmailHost.length; i++){
            if(input.endsWith(verifiedEmailHost[i])){
                return true; //valid
            }
        }
        return false;
    }

    public static Boolean validatePassword(String input){
//        if(validateInput(input) == false || input.length() < 5 || input.length() > 15){
//            return false;
//        }
//        for(int i=0; i<input.length(); i++){
//            if(!Character.isAlphabetic(input.indexOf(i)) && !Character.isDigit(input.indexOf(i))){
//                return false;
//            }
//        }
        return true; //valid
    }

    public static Boolean validateUsername(String input){
//        if(validateInput(input) == false || input.length() < 5 || input.length() > 20){
//            return false;
//        }
//        for(int i=0; i<input.length(); i++){
//            if(!Character.isAlphabetic(input.indexOf(i)) && !Character.isDigit(input.indexOf(i))){
//                return false;
//            }
//        }
        return true; //valid
    }

    public static void showSuccessMessage(Context context, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.verified_logo);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }

    public static void showAlertMessage(Context context, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_warning);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }
}
