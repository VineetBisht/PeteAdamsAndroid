package com.example.pete.ui.quick_fixes;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pete.R;
import com.example.pete.ui.home.DateAdapter;
import com.example.pete.ui.home.HomeAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class QuickFixFragment extends Fragment{
    RecyclerView recyclerView,recyclerViewDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quickfix, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.fixRecyclerview);
        LinearLayoutManager horizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        ImageView direction=root.findViewById(R.id.direction);

        QuickFixAdapter adapter = new QuickFixAdapter(getOptions(),getOptionDetails(),direction);

        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(adapter);

        return root;
    }

    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Check Circuit Breakers");
        options.add("Use a Plunger");
        options.add("New Equipment Warranty?");
        return options;
    }

    public static ArrayList<String> getOptionDetails() {
        ArrayList<String> optionDetails = new ArrayList<>();
        optionDetails.add("Appliance failures are often due to 'tripped' GFI's or circuit breakers. GFI receptacles have a RESET between the outlets. " +
                "Look in Panel K for kitchen circuits or Panel L for lobby circuits. Please call if you can't locate a panel or breaker switch.");
        optionDetails.add("Clogged sinks and toilets can usually be cleared quickly and easily with a plunger." +
                " Removing drain strainers is a sure way to cause unnecessary problems.");
        optionDetails.add("Most new restaurant appliances come with warranty coverage for a year or more. Repairs must be done by factory technicians. " +
                "Save time and expense by contacting the manufacturer or H&K. Many of our toasters and convection ovens are still under warranty.");
        return optionDetails;
    }
}
