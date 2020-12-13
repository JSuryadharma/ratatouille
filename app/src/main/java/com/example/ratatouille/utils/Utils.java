package com.example.ratatouille.utils;

import android.app.AlertDialog;
import android.content.Context;

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
        if(validateInput(input) == false || input.length() < 6 || input.length() > 15){ // password minim 6 karakter.. ketentuan Firebase..
            return false;
        }
        System.out.println("Masuk dua");

        char charInput[] = input.toCharArray();

        for(int i=0; i<input.length(); i++){
            if(!Character.isAlphabetic(charInput[i]) && !Character.isDigit(charInput[i])){
                return false;
            }
        }
        return true; //valid
    }

    public static Boolean validateUsername(String input){
        if(validateInput(input) == false || input.length() < 5 || input.length() > 20){
            return false;
        }

        char charInput[] = input.toCharArray();

        for(int i=0; i<input.length(); i++){
            if(!Character.isAlphabetic(charInput[i]) && !Character.isDigit(charInput[i])){
                return false;
            }
        }
        return true; //valid
    }

    public static Boolean validatePhone(String input){
        if(validateInput(input) == false || input.length() < 11 || input.length() > 12){ // bisa 62 bisa 0
            return false;
        }

        char charInput[] = input.toCharArray();

        for(int i=0; i<input.length(); i++){
            if(!Character.isDigit(charInput[i])){
                return false;
            }
        }
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
