package com.example.ratatouille.models;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.UUID;

public class Vouchers {
    String voucherID;
    String voucherName;
    Integer voucherDisc;

    public Vouchers(String voucherID, String voucherName, Integer voucherDisc){
        this.voucherID = voucherID;
        this.voucherName = voucherName;
        this.voucherDisc = voucherDisc;
    }

    private void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(DatabaseVars.VouchersTable.VOUCHER_TABLE);

        dbRef.child(DatabaseVars.VouchersTable.VOUCHER_ID).setValue(this.voucherID);
        dbRef.child(DatabaseVars.VouchersTable.VOUCHER_NAME).setValue(this.voucherName);
        dbRef.child(DatabaseVars.VouchersTable.VOUCHER_DISC).setValue(this.voucherDisc);
    }

    private void update(String voucherName, Integer voucherDisc){
        this.voucherName = voucherName;
        this.voucherDisc = voucherDisc;
    }
}
