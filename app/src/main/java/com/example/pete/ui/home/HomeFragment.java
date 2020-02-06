package com.example.pete.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

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

        HomeAdapter adapter = new HomeAdapter(getOptions());
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

}