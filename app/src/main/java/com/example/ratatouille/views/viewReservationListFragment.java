package com.example.ratatouille.views;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.ReservationController;
import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.models.Restaurant;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.reservationRecyclerAdapter;
import com.example.ratatouille.utils.restaurantCB;
import com.example.ratatouille.utils.userReservationAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;

public class viewReservationListFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private ScrollView scrollView;
    private LinearLayout backButton;
    private TextView backButton_text;
    private RecyclerView reservation_RecyclerView;
    private ArrayList<ReservationRequest> reservationData;
    private restaurantCB cb = new restaurantCB() {
        @Override
        public void onRestaurantCB(Context context, Restaurant restaurant) {
            setReservationList();
        }
    };

    public viewReservationListFragment(){
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_reservation_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = getView().findViewById(R.id.reservationlist_refresh);
        scrollView = getView().findViewById(R.id.reservationlist_scrollView);
        backButton = getView().findViewById(R.id.reservationlist_backButton);
        backButton_text = getView().findViewById(R.id.reservationlist_backButtonText);
        reservation_RecyclerView = getView().findViewById(R.id.userReservationList);

        customerView.menubar_layout.setVisibility(View.GONE);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0){
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });

        backButton_text.setTextColor(Color.BLACK);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_text.setTextColor(Color.DKGRAY);

                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.personleave);
                player.start();

                Fragment backFragment = new ViewProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, backFragment).commit();
            }
        });
        reload();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                refreshLayout.setEnabled(false);
            }
        });
    }

    private void reload() {
        reservationData = ReservationController.getAllReservations(getContext(), cb, null, VariablesUsed.loggedUser.getUid());
    }

    private void setReservationList() {
        userReservationAdapter ura = new userReservationAdapter(getContext(), reservationData);
        reservation_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reservation_RecyclerView.setAdapter(ura);
    }

}
