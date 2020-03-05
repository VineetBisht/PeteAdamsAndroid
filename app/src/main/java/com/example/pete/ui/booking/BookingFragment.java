package com.example.pete.ui.booking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.pete.R;
public class BookingFragment extends Fragment {

    EditText name,address,contact,email,description;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_booking, container, false);

        name=root.findViewById(R.id.name);
        address=root.findViewById(R.id.address);
        contact=root.findViewById(R.id.contact);
        email=root.findViewById(R.id.email);
        description=root.findViewById(R.id.description);

        // Inflate the layout for this fragment
        return root;
    }
}
