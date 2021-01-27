package com.example.ratatouille.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.UserVoucher;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static com.example.ratatouille.vars.VariablesUsed.currentUser;
import static com.example.ratatouille.vars.VariablesUsed.currentVoucher;

public class VoucherController {

    private static Vouchers searchedVoucher = null;

    public static Vouchers searchVoucher(String voucherName){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.VouchersTable.VOUCHER_TABLE);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().equals(voucherName)) {
                    searchedVoucher = snapshot.getValue(Vouchers.class);
                    Log.w(TAG, "onSuccess: Voucher Searched Successfully!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Voucher Searched Failed!");
            }
        });

        return searchedVoucher;
    }

    public static void useVoucher(Context context, callbackHelper callback) {
        UserVoucher.useVoucher(context, callback, currentUser, currentVoucher);
    }

    public static Vouchers makeVoucher(String voucherName, Integer voucherDisc, Integer voucherPrice){
        Vouchers currentVoucher = new Vouchers(UUID.randomUUID().toString(), voucherName, voucherDisc, voucherPrice);
        currentVoucher.save();
        return currentVoucher;
    }

    public static void buyVoucher(Integer price){
        currentUser.setPoints(currentUser.getPoints() - price);
        currentUser.save();
    }

    public static void deleteVoucher(String voucherID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.VouchersTable.VOUCHER_TABLE).child(VariablesUsed.loggedUser.getUid());
        dbRef.child(voucherID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
                Log.w(TAG, "Data Deletion successful!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Data Deletion failed!");
            }
        });
    }

    public static void assignVoucher(Users user, Vouchers voucher){ // hanya berlaku untuk satu user tertentu, makanya pakai UserVoucher table..
        UserVoucher.save(user, voucher);
    }

    public static Vouchers getUserVoucher(String voucherId){ // hanya berlaku untuk satu user tertentu, makanya pakai UserVoucher table..
        Vouchers selectedVoucher = UserVoucher.getVoucherForAUser(voucherId);
        return selectedVoucher;
    }

    public static ArrayList<Vouchers> getAllUserVoucher(Context context, callbackHelper cb) { // hanya berlaku untuk satu user tertentu, makanya pakai UserVoucher table..
        ArrayList<Vouchers> voucherList = UserVoucher.getAllVoucherForAUser(context, cb);
        return voucherList;
    }

    public static ArrayList<Vouchers> getAllVouchers(Context context, callbackHelper cb) {
        ArrayList<Vouchers> voucherList = Vouchers.getAll(context, cb);
        return voucherList;
    }
}
