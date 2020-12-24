package com.example.ratatouille.db;

public class DatabaseVars {

//    public static final String DB_NAME = "ratatouille_database.db";

    public static final int RC_SIGN_IN = 111;

    public static final class UsersTable {
        public static final String USERS_TABLE = "Users";
        public static final String USER_ID = "UserID";
        public static final String USERNAME = "Username";
        public static final String PASSWORD = "Password";
        public static final String EMAIL = "Email";
        public static final String NAME = "Name";
        public static final String PHONE = "Phone";
        public static final String ADDRESS = "Address";
        public static final String LASTLOGIN = "LastLogin";
    }

    public static final class UserVoucherTable {
        public static final String USERVOUCHER_TABLE = "UserVoucher";
        public static final String USER_ID = "UserID";
        public static final String VoucherID = "VoucherID";
    }

    public static final class VouchersTable {
        public static final String VOUCHER_TABLE = "Vouchers";
        public static final String VOUCHER_ID = "VoucherID";
        public static final String VOUCHER_NAME = "VoucherName";
        public static final String VOUCHER_DISC = "VoucherDisc";
    }

}
