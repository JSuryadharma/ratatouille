package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

// assumed that Details CANNOT BE UPDATED!

public class UserVoucher {

    private static Vouchers selectedVoucher = null;

    public static void save(Users user, Vouchers voucher){ //assigning a user to a voucher.. (Header-to-Details Relation)
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UserVoucherTable.USERVOUCHER_TABLE).child(VariablesUsed.loggedUser.getUid());

        dbRef.child(voucher.getVoucherID()).setValue(voucher); //memasukkan voucher ke dalam UserVoucher table
    }

    public static void delete(String userID, String voucherID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UserVoucherTable.USERVOUCHER_TABLE);
        dbRef.child(userID).child(voucherID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    snapshot.getRef().removeValue();
                    Log.w(TAG, "OnSuccess: User data deleted!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "OnFailure: User data failed to be deleted!");
            }
        });
    }

    public static Vouchers getVoucherForAUser(String voucherID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UserVoucherTable.USERVOUCHER_TABLE).child(VariablesUsed.loggedUser.getUid());

        selectedVoucher = null; // null the voucher selections first

        dbRef.child(voucherID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedVoucher = snapshot.getValue(Vouchers.class);

                Log.w(TAG, "onSuccess: Voucher Data is successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Voucher Data is failed to be retrieved!");
            }
        });
        return selectedVoucher;
    }

    public static ArrayList<Vouchers> getAllVoucherForAUser(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UserVoucherTable.USERVOUCHER_TABLE).child(VariablesUsed.loggedUser.getUid());

        ArrayList<Vouchers> voucherList = new ArrayList<Vouchers>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot currentVoucher : snapshot.getChildren()){
                    Vouchers voucher = currentVoucher.getValue(Vouchers.class);

                    voucherList.add(voucher);
                }
                Log.e(TAG, "onSuccess: Voucher Datas has been Retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onFailure: Voucher Datas failed to be retrieved!");
            }
        });
        return voucherList;
    }

}
