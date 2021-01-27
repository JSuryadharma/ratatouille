package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.controllers.VoucherController;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.vpTermsofService;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.ratatouille.vars.VariablesUsed.currentRestaurant;
import static com.example.ratatouille.vars.VariablesUsed.currentRestoDetail;
import static com.example.ratatouille.vars.VariablesUsed.currentUser;
import static com.example.ratatouille.vars.VariablesUsed.currentVoucher;
import static com.example.ratatouille.vars.VariablesUsed.firstLoginBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 1;
    private SwipeRefreshLayout pulltorefresh;
    private CircleImageView imageProfile;
    private TextView editButton;
    private TextView usernameText;
    private TextView emailText;
    private TextView phoneNumberText;
    private TextView addressText;
    private TextView yourVouchersText;
    private RelativeLayout phoneNumberArea;
    private RelativeLayout addressArea;
    private RelativeLayout yourVoucherArea;
    private RelativeLayout contactSupport;
    private RelativeLayout settings;
    private RelativeLayout termsOfUse;
    private RelativeLayout privacyPolicy;
    private RelativeLayout viewReservationButton;
    private RelativeLayout logoutArea;
    private Context context;

    private ArrayList<Vouchers> voucherList;

    private Utils.response opt_respond = new Utils.response() {
        @Override
        public void yesResponse() {
            MediaPlayer player = MediaPlayer.create(context, R.raw.personleave);
            player.start();
            firstLoginBoolean = false;
            currentRestaurant = null;
            currentUser = null;
            currentRestoDetail = null;
            currentVoucher = null;
            Intent backIntent = new Intent(context, loginScreen.class);
            startActivity(backIntent);
        }

        @Override
        public void noResponse() {
            Toast.makeText(context, "Action cancelled.", Toast.LENGTH_LONG).show();
        }
    };

    private Integer TAKE_IMAGE_CODE = 1001;
    private static Handler handler = null;
    private static Runnable runnable = null;

    callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            yourVouchersText.setText("Currently, You have " + voucherList.size() + " vouchers.");
        }
    };


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfileFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = null;
        context = view.getContext();
        pulltorefresh = getView().findViewById(R.id.vp_pulltorefresh);
        imageProfile = getView().findViewById(R.id.vp_imageProfile);
        editButton = getView().findViewById(R.id.vp_editButton);
        usernameText = getView().findViewById(R.id.vp_username);
        emailText = getView().findViewById(R.id.vp_email);
        phoneNumberText = getView().findViewById(R.id.vp_phoneNumberText);
        addressText = getView().findViewById(R.id.vp_addressText);
        yourVouchersText = getView().findViewById(R.id.vp_yourVouchersText);
        contactSupport = getView().findViewById(R.id.vp_contactSupport);
        settings = getView().findViewById(R.id.vp_settings);
        termsOfUse = getView().findViewById(R.id.vp_termsOfUse);
        privacyPolicy = getView().findViewById(R.id.vp_privacyPolicy);
        viewReservationButton = getView().findViewById(R.id.vp_viewReservations);
        phoneNumberArea = view.findViewById(R.id.vp_phoneNumber);
        addressArea = view.findViewById(R.id.vp_address);
        yourVoucherArea = view.findViewById(R.id.vp_yourVouchers);
        voucherList = VoucherController.getAllUserVoucher(getView().getContext(), cb);
        logoutArea = view.findViewById(R.id.vp_logout);

        reload();

        customerView.menubar_layout.setVisibility(View.VISIBLE);

        //        ProfilePicture Initializations...
        if(VariablesUsed.loggedUser.getPhotoUrl() != null) {
            Glide.with(getView().getContext())
                    .load(VariablesUsed.loggedUser.getPhotoUrl())
                    .into(imageProfile);
        }

        viewReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment reviewCodeFragment = new viewReservationListFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, reviewCodeFragment).commit();
            }
        });

        pulltorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                pulltorefresh.setRefreshing(false);
            }
        });

        phoneNumberArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment editSpecific = new EditSpecificFragment("Phone Number");

                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, editSpecific).commit();
                handler.removeCallbacks(runnable);
            }
        });

        addressArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment editSpecific = new EditSpecificFragment("Address");

                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fade_out).replace(R.id.fragment_container, editSpecific).commit();
                handler.removeCallbacks(runnable);
            }
        });

        yourVoucherArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment voucherFragment = new voucherFragment();

                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, voucherFragment).commit();
                handler.removeCallbacks(runnable);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment editFragment = new EditProfileFragment();

                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, editFragment).commit();
                handler.removeCallbacks(runnable);
            }
        });

        imageProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // mungkin kalau nanti akhir2 bisa diganti jadi select pictures from library..
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(intent, TAKE_IMAGE_CODE);
                }
            }
        });

        contactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment faqFragment = new faqFragment();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, faqFragment).commit();
                handler.removeCallbacks(runnable);
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getView().getContext(), "Settings are not available. Please wait until next version.", Toast.LENGTH_LONG).show();
            }
        });

        termsOfUse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                vpTermsofService.generateTerms();

                Utils.showDialogMessage(R.drawable.ic_warning, getView().getContext(), "Term of Service", vpTermsofService.getTerms());
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getView().getContext(), "Still in Alpha Version. Privacy Policies will be available to users.", Toast.LENGTH_LONG).show();
            }
        });

        logoutArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showOptMessage(view.getContext(), opt_respond, "Confirmation", "Are you sure want to logout?");
            }
        });

        autoRefresh(60000);
    }

    public void autoRefresh(int millis) {
        if(handler == null) {
            handler = new Handler();

            runnable = new Runnable() {
                @Override
                public void run() {
                    reload();
                }
            };
            handler.postDelayed(runnable, millis);
        }
    }

    public void reload(){
        usernameText.setText(VariablesUsed.currentUser.getUsername());
        emailText.setText(VariablesUsed.loggedUser.getEmail());
        phoneNumberText.setText(VariablesUsed.currentUser.getPhone());
        addressText.setText(VariablesUsed.currentUser.getAddress());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageProfile.setImageBitmap(bitmap);
                    UserController.uploadProfilePicture(getContext(), bitmap);
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(getView().getContext(), "Image Selection Has Been Cancelled!", Toast.LENGTH_LONG).show();
            }
        }
    }
}