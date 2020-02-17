package com.example.pete.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;
import com.example.pete.ui.cost_reduction.CostReductionFragment;
import com.example.pete.ui.quick_fixes.QuickFixFragment;

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
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerViewDate.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        recyclerViewDate.setAdapter(dateAdapter);

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
                break;

        }
    }
}