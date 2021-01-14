package com.example.ratatouille.views;

import android.media.Image;
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

public class EditSpecificFragment extends Fragment {
    String mode;
    ImageView backButton;
    ImageView profileImage;
    TextView profileUsername;
    TextView profileEmail;
    TextView toBeLabel;
    EditText toBeInput;
    TextView saveButton;

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

        if(VariablesUsed.loggedUser.getPhotoUrl() != null) {
            Glide.with(getView().getContext())
                    .load(VariablesUsed.loggedUser.getPhotoUrl())
                    .into(profileImage);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProfileFragment backPage = new ViewProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backPage).commit();
            }
        });

        switch(mode){
            case "Phone Number":
                toBeLabel.setText("Phone Number");
                toBeInput.setText(VariablesUsed.currentUser.getUsername());
                break;
            case "Address":
                toBeLabel.setText("Address");
                toBeInput.setText(VariablesUsed.currentUser.getAddress());
                break;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("Phone Number")){
                    if(Utils.validatePhone(toBeInput.getText().toString())){
                        UserController.updateProfile(VariablesUsed.currentUser.getUsername(), VariablesUsed.currentUser.getName(), toBeInput.getText().toString(), VariablesUsed.currentUser.getAddress(), VariablesUsed.currentUser.getPoints());
                        final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Profile Updated", "Reloading the new data!");
                        ft.detach(EditSpecificFragment.this);
                        ft.attach(EditSpecificFragment.this);
                        ft.commit();
                    } else {
                        toBeInput.setError("Invalid Phone Input!");
                    }
                }
                if(mode.equals("Address")){
                    if(Utils.validateInput(toBeInput.getText().toString())){
                        UserController.updateProfile(VariablesUsed.currentUser.getUsername(), VariablesUsed.currentUser.getName(),  VariablesUsed.currentUser.getPhone(), toBeInput.getText().toString(), VariablesUsed.currentUser.getPoints());
                        final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        Utils.showDialogMessage(R.drawable.verified_logo, getView().getContext(), "Profile Updated", "Reloading the new data!");
                        ft.detach(EditSpecificFragment.this);
                        ft.attach(EditSpecificFragment.this);
                        ft.commit();
                    } else {
                        toBeInput.setError("Invalid Address Input!");
                    }
                }
            }
        });

    }
}
