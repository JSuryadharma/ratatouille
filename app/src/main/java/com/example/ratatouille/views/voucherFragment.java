package com.example.ratatouille.views;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class voucherFragment extends Fragment {
    private SwipeRefreshLayout pullToRefresh;
    private LinearLayout backButton;
    private TextView backButton_text;
    private TextView header;
    private TextView points;
    private EditText searchBox;
    private TextView nothingToShow;
    private ViewPager voucher_viewPager;
    private PagerAdapter voucheradapter;
    private TextView noVoucher;
    private RecyclerView voucher_recyclerView;
    private com.example.ratatouille.utils.voucherRecyclerAdapter voucherrecycleradapter;
    private ArrayList<Vouchers> myVoucher;
    private ArrayList<Vouchers> voucherStore;
    private static Handler handler = null;
    private static Runnable runnable = null;

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
        pullToRefresh = view.findViewById(R.id.voucher_pulltorefresh);
        backButton = view.findViewById(R.id.voucher_backButton);
        backButton_text = view.findViewById(R.id.voucher_backButton_text);
        header= view.findViewById(R.id.voucher_header);
        points = view.findViewById(R.id.voucher_header_points);
        searchBox = view.findViewById(R.id.voucher_searchBox);
        nothingToShow = view.findViewById(R.id.voucher_nothing);
        voucher_viewPager = view.findViewById(R.id.voucher_viewPager);
        noVoucher = view.findViewById(R.id.voucher_noVoucher);
        voucher_recyclerView = view.findViewById(R.id.voucher_recylerView);

        myVoucher = VoucherController.getAllUserVoucher(getView().getContext(), cb);
        voucherStore = VoucherController.getAllVouchers(getView().getContext(), cb);

        customerView.menubar_layout.setVisibility(View.GONE);

        //Refresh Listener
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                pullToRefresh.setRefreshing(false);
            }
        });

        backButton_text.setTextColor(Color.WHITE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);
                Fragment backFragment = new ViewProfileFragment();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.fragment_container, backFragment).commit();
                handler.removeCallbacks(runnable);
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
                    voucherStore = VoucherController.getAllVouchers(getView().getContext(), cb);
                } else {
                    //If SearchBox is used..
                    ArrayList<Vouchers> voucherResults = new ArrayList<>();
                    for(int i=0; i < voucherStore.size(); i++){
                        if(Utils.matchString(voucherStore.get(i).getVoucherName(), searchBox.getText().toString())){
                            System.out.println("print: " + voucherStore.get(i).getVoucherName());
                            voucherResults.add(voucherStore.get(i));
                        }
                    }
                    showSearchVoucherResults(voucherResults);
                }
            }
        });

        reload();

        //set the refresh content handler
        refreshContent(60000);

    }

    public void refreshContent(int millis) {
        if(handler == null) {
            handler = new Handler();

            runnable = new Runnable() {
                @Override
                public void run() {
                    //Dont interrupt searching process..
                    if(searchBox.getText().toString().equals("")) {
                        reload();
                    }
                }
            };

            // Update every x milliseconds
            handler.postDelayed(runnable, millis);
        }
    }

    public void reload() {
        header.setText(VariablesUsed.currentUser.getName() + ",");
        points.setText(VariablesUsed.currentUser.getPoints().toString());
        myVoucher = VoucherController.getAllUserVoucher(this.getContext(), cb);
        voucherStore = VoucherController.getAllVouchers(this.getContext(), cb);
    }

    public void showVoucherStoreResults(){
        if (voucherStore.size() > 0) {
            nothingToShow.setVisibility(View.GONE);
            voucher_viewPager.setVisibility(View.VISIBLE);
            // Setting the Voucher Store View Pager
            voucheradapter = new voucherAdapter(this.getContext(), voucherStore);
            voucher_viewPager.setAdapter(voucheradapter);
            voucher_viewPager.setClipToPadding(false);
            voucher_viewPager.setPadding(120, 0, 120, 0);
            voucher_viewPager.setPageMargin(60);
            voucher_viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    if (voucher_viewPager.getCurrentItem() == 0) {
                        page.setTranslationX(-120);
                    } else if(voucher_viewPager.getCurrentItem() == voucheradapter.getCount() - 1){
                        page.setTranslationX(120);
                    } else {
                        page.setTranslationX(0);
                    }
                }
            });
        } else {
            nothingToShow.setVisibility(View.VISIBLE);
            voucher_viewPager.setVisibility(View.GONE);
        }
    }

    public void showMyVoucherResults() {
        if(myVoucher.size() > 0){
            noVoucher.setVisibility(View.GONE);
            // Setting the Voucher Recycler View
            voucher_recyclerView.setVisibility(View.VISIBLE);
            voucherrecycleradapter = new voucherRecyclerAdapter(this.getContext(), myVoucher);
            voucher_recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            voucher_recyclerView.setAdapter(voucherrecycleradapter);
        } else {
            noVoucher.setVisibility(View.VISIBLE);
            voucher_recyclerView.setVisibility(View.GONE);
        }
    }

    public void showSearchVoucherResults(ArrayList<Vouchers> voucherResults){
        if (voucherResults.size() > 0) {
            nothingToShow.setVisibility(View.GONE);
            voucher_viewPager.setVisibility(View.VISIBLE);
            voucheradapter = new voucherAdapter(this.getContext(), voucherResults);
            voucher_viewPager.setAdapter(voucheradapter);
            voucher_viewPager.setClipToPadding(false);
            voucher_viewPager.setPadding(120, 0, 120, 0);
            voucher_viewPager.setPageMargin(60);
            voucher_viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    if (voucher_viewPager.getCurrentItem() == 0) {
                        page.setTranslationX(-120);
                    } else if(voucher_viewPager.getCurrentItem() == voucheradapter.getCount() - 1){
                        page.setTranslationX(120);
                    } else {
                        page.setTranslationX(0);
                    }
                }
            });
        } else {
            nothingToShow.setVisibility(View.VISIBLE);
            voucher_viewPager.setVisibility(View.GONE);
        }
    }
}
