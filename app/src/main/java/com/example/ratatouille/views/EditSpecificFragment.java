package com.example.ratatouille.views;

import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditSpecificFragment extends Fragment {
    private String mode;
    private ImageView backButton;
    private CircleImageView profileImage;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView toBeLabel;
    private EditText toBeInput;
    private TextView saveButton;

    public EditSpecificFragment(String mode){
        this.mode = mode;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_specific, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.esp_backbutton);
        profileUsername = view.findViewById(R.id.esp_username);
        profileEmail = view.findViewById(R.id.esp_email);
        toBeLabel = view.findViewById(R.id.esp_toBeLabel);
        toBeInput = view.findViewById(R.id.esp_toBeFilled);
        saveButton = view.findViewById(R.id.esp_saveButton);
        profileImage = view.findViewById(R.id.esp_imageProfile);

        profileUsername.setText(VariablesUsed.currentUser.getUsername());
        profileEmail.setText(VariablesUsed.loggedUser.getEmail());

        customerView.menubar_layout.setVisibility(View.GONE);

        if(VariablesUsed.loggedUser.getPhotoUrl() != null) {
            Glide.with(getView().getContext())
                    .load(VariablesUsed.loggedUser.getPhotoUrl())
                    .into(profileImage);
        }

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

        switch(mode){
            case "Phone Number":
                toBeLabel.setText("Phone Number");
                toBeInput.setHint(VariablesUsed.currentUser.getPhone());
                break;
            case "Address":
                toBeLabel.setText("Address");
                toBeInput.setHint(VariablesUsed.currentUser.getAddress());
                break;
        }

        saveButton.setTextColor(Color.WHITE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setTextColor(Color.DKGRAY);
                if(mode.equals("Phone Number")){
                    if(Utils.validateInput(toBeInput.getText().toString()) && Utils.validatePhone(toBeInput.getText().toString())){
                        UserController.updateProfile(VariablesUsed.currentUser.getUsername(), VariablesUsed.currentUser.getName(), toBeInput.getText().toString(), VariablesUsed.currentUser.getAddress(), VariablesUsed.currentUser.getPoints());
                        Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Profile Updated", "Reloading the new data!");
                    } else {
                        toBeInput.setError("Invalid Phone Input!");
                    }
                }
                if(mode.equals("Address")){
                    if(Utils.validateInput(toBeInput.getText().toString())){
                        UserController.updateProfile(VariablesUsed.currentUser.getUsername(), VariablesUsed.currentUser.getName(),  VariablesUsed.currentUser.getPhone(), toBeInput.getText().toString(), VariablesUsed.currentUser.getPoints());
                        Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Profile Updated", "Reloading the new data!");
                    } else {
                        toBeInput.setError("Invalid Address Input!");
                    }
                }
            }
        });

    }
}
