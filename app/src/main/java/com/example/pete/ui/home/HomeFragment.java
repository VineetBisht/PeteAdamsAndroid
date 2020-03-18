package com.example.pete.ui.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.pete.R;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements HomeAdapter.OnPagerListener{

    RecyclerView recyclerViewDate;
    ViewPager viewPager;
    LinearLayout sliderDots;
    ArgbEvaluator argbEvaluator;
    private int dots_count;
    private ImageView[] dots;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        argbEvaluator = new ArgbEvaluator();

        viewPager = root.findViewById(R.id.viewPager);
        recyclerViewDate = (RecyclerView) root.findViewById(R.id.recyclerviewDate);

        LinearLayoutManager horizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HomeAdapter adapter = new HomeAdapter(getOptions(), getContext(),this);
        DateAdapter dateAdapter=new DateAdapter(getDateOptions());

        recyclerViewDate.setLayoutManager(new LinearLayoutManager(getContext()));
        viewPager.setAdapter(adapter);
        recyclerViewDate.setAdapter(dateAdapter);

        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPadding(20, 0, 20, 0);

        sliderDots = root.findViewById(R.id.sliderDots);
        dots_count=adapter.getCount();
        dots = new ImageView[dots_count];
        for(int i=0;i<dots_count;i++){
            dots[i]=new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));

        final Integer[] colors = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
        };

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ConstraintLayout mainPage= root.findViewById(R.id.mainPage);

                if (position < (dots_count - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );

                    mainPage.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                    mainPage.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for(ImageView i:dots){
                i.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return root;
    }

    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Quick Fixes");
        options.add("Cost Reduction");
        options.add("New Booking");
        return options;
    }

    public static HashMap<Integer,String> getDateOptions() {
        HashMap<Integer,String> options = new HashMap<>();
        options.put(1,"10 Jan, 2020");
        options.put(2,"12 Feb, 2020");
        options.put(3,"25 Mar, 2020");
        return options;
    }

    @Override
    public void onPagerClick(int position) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (position){
                    case 0:
                        navController.navigate(R.id.quickFixFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.costReductionFragment);
                        break;
                    case 2:
                        navController.navigate(R.id.bookingMain);
                        break;
                }
    }
}