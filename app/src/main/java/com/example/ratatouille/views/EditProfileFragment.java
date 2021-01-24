package com.example.ratatouille.views;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {
    private SwipeRefreshLayout pullToRefresh;
    private ImageView backButton;
    private CircleImageView profileImage;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView saveButton;
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputEmail;
    private EditText inputPhoneNumber;
    private EditText inputAddress;
    private EditText inputName;

    public EditProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefresh = view.findViewById(R.id.ep_pulltorefresh);
        profileUsername = view.findViewById(R.id.ep_username);
        profileEmail = view.findViewById(R.id.ep_email);
        inputUsername = view.findViewById(R.id.ep_usernameInput);
        inputEmail = view.findViewById(R.id.ep_emailInput);
        inputPassword = view.findViewById(R.id.ep_passwordInput);
        inputPhoneNumber = view.findViewById(R.id.ep_phoneNumberInput);
        inputAddress = view.findViewById(R.id.ep_addressInput);
        inputName = view.findViewById(R.id.ep_nameInput);
        backButton = view.findViewById(R.id.ep_backbutton);
        saveButton = view.findViewById(R.id.ep_saveButton);
        profileImage = view.findViewById(R.id.ep_imageProfile);

        customerView.menubar_layout.setVisibility(View.GONE);

        if(VariablesUsed.loggedUser.getPhotoUrl() != null) {
            Glide.with(getView().getContext())
                    .load(VariablesUsed.loggedUser.getPhotoUrl())
                    .into(profileImage);
        }

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                pullToRefresh.setRefreshing(false);
            }
        });

        inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        reload();

        backButton.setColorFilter(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton.setColorFilter(Color.DKGRAY);
                ViewProfileFragment backPage = new ViewProfileFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, backPage).commit();
            }
        });

        saveButton.setTextColor(Color.WHITE);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveButton.setTextColor(Color.DKGRAY);
                Boolean falseChecker = false;
                String updateUsername = VariablesUsed.currentUser.getUsername();
                String updateEmail = VariablesUsed.loggedUser.getEmail();
                String updatePassword = "";
                String updatePhoneNumber = VariablesUsed.currentUser.getPhone();
                String updateAddress = VariablesUsed.currentUser.getAddress();
                String updateName = VariablesUsed.currentUser.getName();

                if(Utils.validateInput(inputUsername.getText().toString())){
                    if(!Utils.validateUsername(inputUsername.getText().toString())) {
                        inputUsername.setError("Invalid Input!");
                        falseChecker = true;
                    } else {
                        updateUsername = inputUsername.getText().toString();
                    }
                }

                if(Utils.validateInput(inputEmail.getText().toString())) {
                    if (!Utils.validateEmail(inputEmail.getText().toString())) {
                        inputEmail.setError("Invalid Input!");
                        falseChecker = true;
                    } else {
                        updateEmail = inputEmail.getText().toString();
                    }
                }
                if(Utils.validateInput(inputPassword.getText().toString())){
                    if(!Utils.validatePassword(inputPassword.getText().toString())){
                        inputPassword.setError("Invalid Input!");
                        falseChecker = true;
                    } else {
                        updatePassword = inputPassword.getText().toString();
                    }
                }
                if(Utils.validateInput(inputPhoneNumber.getText().toString())){
                    if(!Utils.validatePhone(inputPhoneNumber.getText().toString())) {
                        inputPhoneNumber.setError("Invalid Input!");
                        falseChecker = true;
                    } else {
                        updatePhoneNumber = inputPhoneNumber.getText().toString();
                    }
                }
                if(Utils.validateInput(inputAddress.getText().toString())){
                    updateAddress = inputAddress.getText().toString();
                }
                if(Utils.validateInput(inputName.getText().toString())) {
                    updateName = inputName.getText().toString();
                }

                if(falseChecker == false){
                    UserController.updateProfile(updateUsername, updateName, updatePhoneNumber, updateAddress, VariablesUsed.currentUser.getPoints());
                    if(Utils.validateInput(updatePassword)) {
                        UserController.updatePassword(updatePassword);
                    }
                    // Reload current fragment
                    final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                    player.start();
                    Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Profile Updated", "Reloading the new data!");
                } else {
                    MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                    player.start();
                    Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Credentials are not valid!", "Please fill in the indicated textfield correctly!");
                }
            }
        });
    }

    public void reload(){
        profileUsername.setHint(VariablesUsed.currentUser.getUsername());
        profileEmail.setHint(VariablesUsed.loggedUser.getEmail());

        inputUsername.setHint(VariablesUsed.currentUser.getUsername());
        inputEmail.setHint(VariablesUsed.loggedUser.getEmail());
        inputPassword.setHint("********");
        inputPhoneNumber.setHint(VariablesUsed.currentUser.getPhone());
        inputAddress.setHint(VariablesUsed.currentUser.getAddress());
        inputName.setHint(VariablesUsed.currentUser.getName());
    }
}
