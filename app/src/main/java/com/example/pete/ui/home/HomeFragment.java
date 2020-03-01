package com.example.pete.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements HomeAdapter.OptionListener {

    RecyclerView recyclerView,recyclerViewDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerViewDate = (RecyclerView) root.findViewById(R.id.recyclerviewDate);

        LinearLayoutManager horizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HomeAdapter adapter = new HomeAdapter(getOptions(),this);
        DateAdapter dateAdapter=new DateAdapter(getDateOptions());

        recyclerViewDate.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDate.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerViewDate.setAdapter(dateAdapter);

        final int radius = getResources().getDimensionPixelSize(R.dimen.radius);
        final int dotsHeight = getResources().getDimensionPixelSize(R.dimen.dots_height);
        final int color = ContextCompat.getColor(getContext(), R.color.navyBlue);
        recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

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
    public void onOptionClick(int position) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (position){
            case 0:
                navController.navigate(R.id.quickFixFragment);
                break;
            case 1:
                navController.navigate(R.id.costReductionFragment);
                break;
            case 2:
                Toast.makeText(getContext() ,"Yet to be implemented", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}