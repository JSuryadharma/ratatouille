package com.example.ratatouille.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.UserController;
import com.example.ratatouille.controllers.VoucherController;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.voucherAdapter;
import com.example.ratatouille.utils.voucherRecyclerAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;
import java.util.UUID;

public class voucherFragment extends Fragment {
    LinearLayout backButton;
    TextView header;
    TextView points;
    EditText searchBox;
    TextView nothingToShow;
    ViewPager voucher_viewPager;
    PagerAdapter voucheradapter;
    ImageView nextArrow;
    TextView noVoucher;
    RecyclerView voucher_recyclerView;
    TextView refreshButton;
    com.example.ratatouille.utils.voucherRecyclerAdapter voucherrecycleradapter;
    ArrayList<Vouchers> myVoucher;
    ArrayList<Vouchers> voucherStore;

    callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            if(myVoucher.size() > 0) {
                showMyVoucherResults();
            }
            if(voucherStore.size() > 0){
                showVoucherStoreResults();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voucher_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.voucher_backButton);
        header= view.findViewById(R.id.voucher_header);
        points = view.findViewById(R.id.voucher_header_points);
        searchBox = view.findViewById(R.id.voucher_searchBox);
        nothingToShow = view.findViewById(R.id.voucher_nothing);
        voucher_viewPager = view.findViewById(R.id.voucher_viewPager);
        noVoucher = view.findViewById(R.id.voucher_noVoucher);
        voucher_recyclerView = view.findViewById(R.id.voucher_recylerView);
        nextArrow = view.findViewById(R.id.voucher_nextArrow);
        refreshButton = view.findViewById(R.id.voucher_refreshButton);
        myVoucher = VoucherController.getAllUserVoucher(getView().getContext(), cb);
        voucherStore = VoucherController.getAllVouchers(getView().getContext(), cb);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment backFragment = new ViewProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backFragment).commit();
            }
        });

        header.setText(VariablesUsed.currentUser.getName() + ",");
        points.setText(VariablesUsed.currentUser.getPoints().toString());

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getParentFragmentManager().beginTransaction();
                fm.detach(voucherFragment.this);
                fm.attach(voucherFragment.this);
            }
        });

        // For searching new voucher (in-store)

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(voucherStore.size() <= 0){
                    return;
                }
                if(searchBox.getText().toString().equals("")) {
                    //If SearchBox not used..
                    showVoucherStoreResults();
                } else {
                    //If SearchBox is used..
                    ArrayList<Vouchers> voucherResults = new ArrayList<>();
                    for(int i=0; i < voucherStore.size(); i++){
                        if(Utils.matchString(voucherStore.get(i).getVoucherName(), searchBox.getText().toString())){
                            voucherResults.add(voucherStore.get(i));
                        }
                    }
                    showSearchVoucherResults(voucherResults);
                }
                //Refresh the fragment..
                FragmentTransaction fm = getParentFragmentManager().beginTransaction();
                fm.detach(voucherFragment.this);
                fm.attach(voucherFragment.this);
            }
        });

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
            }
        });

    }

    public void showVoucherStoreResults(){
        if (voucherStore.size() > 0) {
            nothingToShow.setVisibility(View.GONE);
            voucheradapter = new voucherAdapter(getView().getContext(), voucherStore);
            voucher_viewPager.setAdapter(voucheradapter);
        } else {
            nothingToShow.setVisibility(View.VISIBLE);
            voucher_viewPager.setVisibility(View.GONE);
        }
        //Refresh the fragment..
        FragmentTransaction fm = getParentFragmentManager().beginTransaction();
        fm.detach(voucherFragment.this);
        fm.attach(voucherFragment.this);
    }

    public void showMyVoucherResults() {
        if(myVoucher.size() > 0){
            noVoucher.setVisibility(View.GONE);
            voucherrecycleradapter = new voucherRecyclerAdapter(getView().getContext(), myVoucher);
            voucher_recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            voucher_recyclerView.setAdapter(voucherrecycleradapter);
        } else {
            noVoucher.setVisibility(View.VISIBLE);
            nextArrow.setVisibility(View.GONE);
            voucher_recyclerView.setVisibility(View.GONE);
        }
        //Refresh the fragment..
        FragmentTransaction fm = getParentFragmentManager().beginTransaction();
        fm.detach(voucherFragment.this);
        fm.attach(voucherFragment.this);
    }

    public void showSearchVoucherResults(ArrayList<Vouchers> voucherResults){
        if (voucherResults.size() > 0) {
            nothingToShow.setVisibility(View.GONE);
            nextArrow.setVisibility(View.GONE);
            voucheradapter = new voucherAdapter(getView().getContext(), voucherResults);
            voucher_viewPager.setAdapter(voucheradapter);
            voucher_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == voucherResults.size() - 1){
                        nextArrow.setVisibility(View.INVISIBLE);
                        System.out.println(position);
                    } else {
                        nextArrow.setVisibility(View.VISIBLE);
                        System.out.println(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            nothingToShow.setVisibility(View.VISIBLE);
            voucher_viewPager.setVisibility(View.GONE);
        }

        //Refresh the fragment..
        FragmentTransaction fm = getParentFragmentManager().beginTransaction();
        fm.detach(voucherFragment.this);
        fm.attach(voucherFragment.this);
    }
}
