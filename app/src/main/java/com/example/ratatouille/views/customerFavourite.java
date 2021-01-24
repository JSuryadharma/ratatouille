package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.FavouritesController;
import com.example.ratatouille.controllers.ReviewController;
import com.example.ratatouille.controllers.restoDetailController;
import com.example.ratatouille.models.Favourite;
import com.example.ratatouille.models.Review;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.utils.callbackHelper;
import com.example.ratatouille.utils.favouritesRecyclerAdapter;
import com.example.ratatouille.utils.reviewRecyclerAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link customerFavourite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customerFavourite extends Fragment {

    private SwipeRefreshLayout pullToRefresh;
    private RecyclerView favouritesView;
    private ArrayList<Favourite> favList;
    private favouritesRecyclerAdapter favAdapter;
    private callbackHelper cb = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            refreshFavouriteList();
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public customerFavourite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customerFavourite.
     */
    // TODO: Rename and change types and number of parameters
    public static customerFavourite newInstance(String param1, String param2) {
        customerFavourite fragment = new customerFavourite();
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
        return inflater.inflate(R.layout.fragment_customer_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerView.menubar_layout.setVisibility(View.VISIBLE);

        pullToRefresh = getView().findViewById(R.id.favourites_pulltorefresh);
        favouritesView = getView().findViewById(R.id.favourites_recyclerview);

        favouritesView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        reload();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    public void reload(){
        favList = FavouritesController.getAllFavouritesForAUser(getView().getContext(), cb, VariablesUsed.loggedUser.getUid());
    }

    public void refreshFavouriteList() {
        favAdapter = new favouritesRecyclerAdapter(getView().getContext(), favList);
        favouritesView.setAdapter(favAdapter);
    }
}