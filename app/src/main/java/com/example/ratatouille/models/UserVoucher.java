package com.example.ratatouille.models;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.google.firebase.database.DatabaseReference;

public class UserVoucher { // this class for linking datas between Users Class and Vouchers Class
    String userID;
    String voucherID;

    public UserVoucher(String userID, String voucherID){
        this.userID = userID;
        this.voucherID = voucherID;
    }

    private void save(){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference().child(DatabaseVars.UserVoucherTable.USERVOUCHER_TABLE);

        dbRef.child(DatabaseVars.UserVoucherTable.USER_ID).setValue(this.userID);
        dbRef.child(DatabaseVars.UserVoucherTable.VoucherID).setValue(this.voucherID);
    }
}
