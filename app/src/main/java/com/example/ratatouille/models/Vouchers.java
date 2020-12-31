package com.example.ratatouille.models;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.UUID;

public class Vouchers {
    String voucherID;
    String voucherName;
    Integer voucherDisc;

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

    public String getVoucherID() { return voucherID; }

    public String getVoucherName() { return voucherName; }

    public Integer getVoucherDisc() { return voucherDisc; }
}
