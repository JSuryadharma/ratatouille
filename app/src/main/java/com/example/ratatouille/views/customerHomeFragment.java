package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille.R;
import com.example.ratatouille.models.mostPopularModels;
import com.example.ratatouille.models.trendingModels;
import com.example.ratatouille.utils.mostPopularAdapter;
import com.example.ratatouille.utils.trendingAdapter;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link customerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customerHomeFragment extends Fragment {
    private SwipeRefreshLayout pullToRefresh;
    private PagerAdapter trendingAdapter;
    private ViewPager trendingView;
    private RecyclerView mostPopular_recyclerView;
    private com.example.ratatouille.utils.mostPopularAdapter mostPopularAdapter;
    private TextView searchBox;
    private Context context;
    private ArrayList<trendingModels> trendingList;
    private ArrayList<mostPopularModels> mostPopularList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public customerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customerHome.
     */
    // TODO: Rename and change types and number of parameters
    public static customerHomeFragment newInstance(String param1, String param2) {
        customerHomeFragment fragment = new customerHomeFragment();
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
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefresh = getView().findViewById(R.id.home_pulltorefresh);
        trendingView = getView().findViewById(R.id.trending_viewPager);
        searchBox = getView().findViewById(R.id.searchBox);
        context = this.getContext();
        trendingList = new ArrayList<>();
        VariablesUsed.previousState = "home";

        customerView.menubar_layout.setVisibility(View.VISIBLE);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.open);
                player.start();
                pullToRefresh.setRefreshing(false);
            }
        });

        searchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Intent intent = new Intent(context, Search.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("q", searchBox.getText().toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });

        mostPopular_recyclerView = getView().findViewById(R.id.mostPopular_recyclerview);
        mostPopular_recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Setting Trending Adapter
        reload();
    }

    private void loadTrending() {
        trendingList = new ArrayList<>();
        trendingList.add(new trendingModels("18634744", "Burger King - Living World, Serpong Utara", "Fast Food, Burger", (float) 3.2, 125000, "https://b.zmtcdn.com/data/pictures/chains/3/7400163/fac80eb0f665717de0d3ee3314d140c5.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        trendingList.add(new trendingModels("7414651", "Roti Bakar 88 - 88 Food Court, Kec. Tangerang", "Desserts, Street Food, Indonesian", (float) 3.4, 80000, "https://b.zmtcdn.com/data/pictures/chains/1/7412021/a37acda51c150df6a1f11225ed5b54e5.jpeg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        trendingList.add(new trendingModels("7410162", "Martabak Pecenongan 65A - Pecenongan", "Street Food, Martabak", (float) 4.3, 120000, "https://b.zmtcdn.com/data/pictures/chains/2/7410162/093f2394b94c64c0c465736865ebd835.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        trendingAdapter = new trendingAdapter(this.getContext(), trendingList);
        trendingView.setAdapter(trendingAdapter);
        trendingView.setClipToPadding(false);
        trendingView.setPadding(120, 0, 120, 0);
        trendingView.setPageMargin(60);
        trendingView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (trendingView.getCurrentItem() == 0) {
                    page.setTranslationX(-120);
                } else if(trendingView.getCurrentItem() == trendingAdapter.getCount() - 1){
                    page.setTranslationX(120);
                } else {
                    page.setTranslationX(0);
                }
            }
        });
    }

    private void loadMostPopularList() {
        mostPopularList = new ArrayList<>();
        mostPopularList.add(new mostPopularModels("Kokumi - Plaza Indonesia, Thamrin", "Bubble Tea, Beverages", (float) 3.4, "10 AM to 10 PM", "https://b.zmtcdn.com/data/pictures/4/18781104/8bf1e5defb6f0807d29b8e3fd91a2ffa.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A", "19342904"));
        mostPopularList.add(new mostPopularModels("KFC - Grand Indonesia Mall, Thamrin", "Fast Food, American", (float) 3.5, "10 AM to 10 PM", "https://b.zmtcdn.com/data/pictures/chains/2/7400132/3727622cf23ce0b3dbd91844ff1abb29.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A", "7401175"));
        mostPopularList.add(new mostPopularModels("Sate Khas Senayan - Senayan City, Senayan", "Satay, Indonesian", (float) 3.3, "10 AM to 10 PM", "https://b.zmtcdn.com/data/res_imagery/7400765_CHAIN_46918d19bbeb1ec1ee407724dc4878dd.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A", "19115601"));
        mostPopularAdapter = new mostPopularAdapter(context, mostPopularList);
        mostPopular_recyclerView.setAdapter(mostPopularAdapter);
    }

    public void reload() {
        loadTrending();
        loadMostPopularList();
    }
}