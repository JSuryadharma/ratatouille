package com.example.ratatouille.db;

import com.google.firebase.database.FirebaseDatabase;

import static com.example.ratatouille.db.DatabaseVars.DB_NAME;
import static com.example.ratatouille.db.DatabaseVars.UsersTable.*;

public class DatabaseHelper {

    static FirebaseDatabase db = FirebaseDatabase.getInstance();

    public static FirebaseDatabase getDb() {
        return db;
    }

//    public DatabaseHelper(@Nullable Context context) {
//        super(context, DB_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        final String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + " (" +
//            USER_ID + " TEXT NOT NULL UNIQUE," +
//            EMAIL + " TEXT NOT NULL UNIQUE," +
//            PASSWORD + " TEXT NOT NULL," +
//            USERNAME + " TEXT NOT NULL UNIQUE," +
//            NAME + " TEXT NOT NULL," +
//            PHONE + " TEXT NOT NULL," +
//            ADDRESS + " TEXT NOT NULL," +
//            LASTLOGIN + " TEXT NOT NULL," +
//            "PRIMARY KEY(" + USER_ID + ")" + ")";
//
//        final String CREATE_USER = String.format("INSERT INTO %s VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', %s)",
//                USERS_TABLE, "a98812af-a59e-49df-bb10-337cf0bc4a23", "test@test.com", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
//                "test", "nama test", "08123456789", "Jalan Venus no 72", "date('now')");
//
//        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
//        sqLiteDatabase.execSQL(CREATE_USER);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
//        onCreate(sqLiteDatabase);
//    }
}
