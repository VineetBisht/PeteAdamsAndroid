package com.example.pete.ui.cost_reduction;

import androidx.lifecycle.ViewModelProviders;

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
import com.example.pete.ui.quick_fixes.QuickFixAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CostReductionFragment extends Fragment {
    RecyclerView recyclerView,recyclerViewDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cost_reduction, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.costRecyclerview);

        LinearLayoutManager horizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        ImageView direction = (ImageView) root.findViewById((R.id.direction));

        CostReductionAdapter adapter = new CostReductionAdapter(getOptions(),getOptionDetails(),direction);

        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Energy Costs");
        options.add("Drain Maintenance");
        options.add("Haste Makes Waste");
        options.add("Water Conservation");
        options.add("Keep Doors Closed");
        options.add("Let Us Know");
        return options;
    }

    private static ArrayList<String> getOptionDetails() {
        ArrayList<String> optionDetails = new ArrayList<>();
        optionDetails.add("Checking and adjusting outside light timers can save energy and reduce costs. " +
                "Return systems to automatic setting as soon as possible after using manual override.  ");
        optionDetails.add("Missing drain strainers are a major cause of back sink clogs and larger grease trap issues. " +
                "SSBS, Auto-bev and icemaker drains should be flushed with hot water nightly to prevent blockages.");
        optionDetails.add("Be sure that walk-in cooler and freezer doors are closed properly to avoid product losses. " +
                "Freezer curtains are too often tied off to the side, defeating their purpose.  ");
        optionDetails.add("Turn off unused water sources and report dripping taps, leaking hoses or other water waste.");
        optionDetails.add("In addition to inviting pests, propped-open exterior doors cause interior air handling imbalance." +
                " HVAC systems become overworked causing guest/crew comfort complaints.");
        optionDetails.add("Our restaurants experience heavy traffic and new repair needs arise hourly. We appreciate being " +
                "informed of any issues before operational, health & safety or guest comfort concerns arise.");
        return optionDetails;
    }
}
