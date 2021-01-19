package com.example.ratatouille.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public static void showDialogMessage(Integer resId, Context context, String title, String message){
        Dialog showDialog = new Dialog(context);
        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.setContentView(R.layout.dialog_view);

        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleDialog = showDialog.findViewById(R.id.dialog_title);
        TextView textDialog = showDialog.findViewById(R.id.dialog_information);
        ImageView logoDialog = showDialog.findViewById(R.id.dialog_logo);
        LinearLayout buttonDialog = showDialog.findViewById(R.id.dialog_button);

        titleDialog.setText(title);
        textDialog.setText(message);
        logoDialog.setImageResource(resId);

        buttonDialog.setBackgroundResource(R.drawable.round_button);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDialog.setBackgroundResource(R.drawable.pressed_round_button);
                MediaPlayer play = MediaPlayer.create(context, R.raw.open);
                play.start();
                showDialog.dismiss();
            }
        });

        showDialog.show();
    }

    public static Boolean matchString(String string1, String string2){
        String[] stringlist1 = string1.split(" ");
        String[] stringlist2 = string2.split(" ");
        Integer counter = 0;
        Integer idx = 0;

        for(int k=0; k<stringlist2.length; k++) {
            if (stringlist2[k].equals(stringlist1[idx])) {
                System.out.println("Comparison: " + stringlist1[idx] + " " + stringlist2[k]);
                counter++;
                idx++;
            } else {
                return false;
            }
        }
        return true;
    }
}
