package com.example.ratatouille.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link customerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customerHomeFragment extends Fragment {

    PagerAdapter trendingAdapter;
    ViewPager trendingView;
    RecyclerView mostPopular_recyclerView;
    com.example.ratatouille.utils.mostPopularAdapter mostPopularAdapter;
    ImageView nextArrow;
    TextView searchBox;
    Context context;

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

        trendingView = getView().findViewById(R.id.trending_viewPager);
        nextArrow = getView().findViewById(R.id.home_nextArrow);
        searchBox = getView().findViewById(R.id.searchBox);
        context = this.getContext();

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

        // Replace this with API
        ArrayList<trendingModels> trendingList = new ArrayList<>();
        trendingList.add(new trendingModels("Starbucks - Alam Sutera", "Cafe, Coffee", (float) 4.5, 60000, "https://cdn.vox-cdn.com/thumbor/z2Oc0Qtvm3Iu1WoHOgohXbX1Ncc=/0x0:5860x4008/1200x800/filters:focal(3075x2118:4011x3054)/cdn.vox-cdn.com/uploads/chorus_image/image/66490225/shutterstock_1410002591.0.jpg"));
        trendingList.add(new trendingModels("Burger King - Grand Indonesia", "Fast Food", (float) 3.4, 45600, "https://miro.medium.com/max/1200/1*pIJH1mPega8583Y3NuPaLg.jpeg"));
        trendingList.add(new trendingModels("Din Tai Fung - Pacific Place", "Chinese Food, Family", (float) 4, 130000, "https://media-cdn.tripadvisor.com/media/photo-s/19/b9/5f/18/din.jpg"));

        trendingAdapter = new trendingAdapter(this.getContext(), trendingList);
        trendingView.setAdapter(trendingAdapter);

        trendingView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == trendingList.size() - 1){
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

        mostPopular_recyclerView = getView().findViewById(R.id.mostPopular_recyclerview);
        mostPopular_recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

//        ArrayList<mostPopularModels> mostPopularList = new ArrayList<>();
//        Calendar myCal = Calendar.getInstance();
//
//        //Replace this with API
//        myCal.set(Calendar.HOUR, 10);
//        myCal.set(Calendar.MINUTE, 00);
//        mostPopularList.add(new mostPopularModels("Banana Leaf - Kemayoran", "Restaurant", (float) 4.5, myCal, "https://3.bp.blogspot.com/-O6iLBqFyGu8/VS9aTbm6tjI/AAAAAAAABj8/_1IZOyytYBs/s1600/Chicken_Satay_on_Banana_Leaf_Java__m.jpg"));
//
//        myCal.set(Calendar.HOUR, 8);
//        myCal.set(Calendar.MINUTE, 00);
//        mostPopularList.add(new mostPopularModels("McDonald's - Sudirman", "Fast Food, Burgers", (float) 5, myCal, "https://i1.wp.com/www.eatthis.com/wp-content/uploads/2020/07/mcdonalds-1.jpg?resize=640%2C360&ssl=1"));
//        mostPopularAdapter = new mostPopularAdapter(this.getContext(), mostPopularList);
//        mostPopular_recyclerView.setAdapter(mostPopularAdapter);

    }
}