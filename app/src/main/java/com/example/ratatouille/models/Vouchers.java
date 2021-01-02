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
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class Vouchers {
    private String voucherID;
    private String voucherName;
    private Integer voucherDisc;

    private static Vouchers selectedValues = null;

    public Vouchers(){
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Vouchers(String voucherID, String voucherName, Integer voucherDisc){
        this.voucherID = voucherID;
        this.voucherName = voucherName;
        this.voucherDisc = voucherDisc;
    }

    public void save(){ // save datas to vouchers table..
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(DatabaseVars.VouchersTable.VOUCHER_TABLE).child(getVoucherID());

        dbRef.child(this.voucherID).setValue(this);
    }

    public void update(String voucherName, Integer voucherDisc){
        this.voucherName = voucherName;
        this.voucherDisc = voucherDisc;
    }

    public static void delete(String voucherID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.VouchersTable.VOUCHER_TABLE);
        dbRef.child(voucherID).removeValue();
    }

    public static Vouchers get(String voucherID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.VouchersTable.VOUCHER_TABLE);

        selectedValues = null;

        dbRef.child(voucherID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Vouchers.class);
                Log.w(TAG, "onSuccess: Vouchers successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Vouchers failed to be retrieved!");
            }
        });

        return selectedValues;
    }

    public static ArrayList<Vouchers> getAll(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.VouchersTable.VOUCHER_TABLE);

        ArrayList<Vouchers> voucherList = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot eachData : snapshot.getChildren()){
                    Vouchers curVoucher = eachData.getValue(Vouchers.class);
                    voucherList.add(curVoucher);
                }
                Log.w(TAG, "onSuccess: All Vouchers successfully retrieved!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: All Vouchers failed to be retrieved!");
            }
        });

        return voucherList;
    }

    public String getVoucherID() { return voucherID; }

    public String getVoucherName() { return voucherName; }

    public Integer getVoucherDisc() { return voucherDisc; }
}
