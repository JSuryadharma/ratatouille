package com.example.ratatouille.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ratatouille.models.UserVoucher;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class UserController {

    public static void UserLogin(callbackHelper cb, Context context, String email, String password) {
        // We use Firebase Auth, for authentication process..
        FirebaseAuth dbAuth = DatabaseHelper.getDbAuth();

        dbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            VariablesUsed.loggedUser = dbAuth.getCurrentUser();
                            if(VariablesUsed.loggedUser.isEmailVerified()) {

                                //success logged in, filling the userdatas via database..

                                DatabaseVars.UsersTable dbVars = new DatabaseVars.UsersTable();
                                DatabaseReference dbRef = DatabaseHelper.getDb().getReference(dbVars.USERS_TABLE).child(VariablesUsed.loggedUser.getUid());

                                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Users currentUser = snapshot.getValue(Users.class);

                                        VariablesUsed.currentUser = currentUser; // update the current logged in user..

                                        updateLastLogin();

                                        VariablesUsed.currentUser.setNumberOfLogins(VariablesUsed.currentUser.getNumberOfLogins() + 1);

                                        VariablesUsed.currentUser.save(); // save the update to User Datas.

                                        // then, call the next intent / screen..
                                        cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "User Data retrieval failed!");
                                    }
                                });

                            } else {
                                Utils.showAlertMessage(context, "Please Verify Account", "To continue, please check verification link on your email account!");
                            }
                        } else {
                            Utils.showAlertMessage(context, "Incorrect Credentials","Please try again, or contact our Customer Service for help.");
                        }
                    }
                });
    }

    public static void UserSignup(Context context, String email, String username, String password, String name, String phone, String address){
        Users newUser = new Users(username, name, phone, address, new Timestamp(System.currentTimeMillis()).toString(), 0, 0);
        Users.register(newUser, email, password);
    }

    public static void firebaseAuthWithGoogle(callbackHelper cb, Context context, String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        DatabaseHelper.getDbAuth().signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            VariablesUsed.loggedUser = DatabaseHelper.getDbAuth().getCurrentUser();

                            DatabaseVars.UsersTable dbVars = new DatabaseVars.UsersTable();
                            DatabaseReference dbRef = DatabaseHelper.getDb().getReference(dbVars.USERS_TABLE).child(VariablesUsed.loggedUser.getUid());

                            VariablesUsed.currentUser = new Users(
                                    VariablesUsed.loggedUser.getEmail().replace("@gmail.com", ""), //username, disamakan dengan name
                                    VariablesUsed.loggedUser.getDisplayName(),
                                    VariablesUsed.loggedUser.getPhoneNumber(),
                                    null, // address
                                    new Timestamp(System.currentTimeMillis()).toString(),
                                    0,
                                    0
                            );

                            dbRef.setValue(VariablesUsed.currentUser);

                            cb.onUserLoadCallback(context, VariablesUsed.currentUser);
                        }
                        else {
                            Utils.showAlertMessage(context, "Failed Login","Please try again, or contact our Customer Service for help.");
                        }
                    }
                });
    }

    public static void updateLastLogin(){
        VariablesUsed.currentUser.setLast_login(new Timestamp(System.currentTimeMillis()).toString());
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.UsersTable.USERS_TABLE).child(VariablesUsed.loggedUser.getUid());
        dbRef.child("last_login").setValue(VariablesUsed.currentUser.getLast_login());
    }


    public static Users updateProfile(String username, String name, String phone, String address, Integer points){
        Users currentUser = VariablesUsed.currentUser.update(username, name, phone, address, VariablesUsed.currentUser.getLast_login(), points);

        return currentUser;
    }

    public static void updatePassword(String password){
        FirebaseUser currentUser = DatabaseHelper.getDbAuth().getCurrentUser();

        currentUser.updatePassword(password);
    }

    public static void uploadProfilePicture(Context context, Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        StorageReference storageRef = DatabaseHelper.getStorage().getReference()
                .child("profileImages")
                .child(VariablesUsed.loggedUser.getUid() + ".jpeg");

        storageRef.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(context, storageRef);
                        Log.w(TAG, "onSuccess: Bitmap to ByteArray");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Bitmap to ByteArray", e.getCause());
                    }
                });
    }

    public static void getDownloadUrl(Context context, StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: get Download URL for Profile Picture" + uri);
                        setUserProfileUrl(context, uri);
                    }
                });
    }

    public static void setUserProfileUrl(Context context, Uri uri){
        FirebaseUser user = VariablesUsed.loggedUser;

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Profile Image Updated Successfully!", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onSuccess: Profile Picture has been Updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Profile Image Failed to Update!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure: Profile Picture failed to update!", e.getCause());
                    }
                });
    }

    public static void refreshUserVoucher(){
        ArrayList<Vouchers> uvList = UserVoucher.getAllVoucherForAUser();
        for(Vouchers curVoucher : uvList){
            Vouchers voucher = Vouchers.get(curVoucher.getVoucherID());
            if(voucher == null){
                uvList.remove(voucher);
                continue;
            }
            uvList.add(voucher);
            VoucherController.assignVoucher(VariablesUsed.currentUser, curVoucher); // reupdate
        }
    }
}
