package com.example.pete.ui.booking;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.pete.R;
import com.google.android.gms.maps.MapView;

public class BookingFragment extends Fragment implements View.OnKeyListener
{

    EditText name, contact, email, description;
    AutoCompleteTextView address;
    DatePicker datePicker;
    MapView mapView;
    boolean error;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_booking, container, false);
        error=false;
        PhoneNumberFormattingTextWatcher contactWatcher=new PhoneNumberFormattingTextWatcher();
        name = root.findViewById(R.id.name);
        address = root.findViewById(R.id.address);
        contact = root.findViewById(R.id.contact);
        email = root.findViewById(R.id.email);
        description = root.findViewById(R.id.description);
        datePicker = root.findViewById(R.id.datePicker);
        mapView = root.findViewById(R.id.mapView);

        name.setOnKeyListener(this);
        address.setOnKeyListener(this);
        address.setAdapter(new PlaceAutoSuggestAdapter(getContext(),android.R.layout.simple_list_item_1));

        contact.setOnKeyListener(this);
        email.setOnKeyListener(this);

        contact.addTextChangedListener(contactWatcher);

        if(contact.getText().toString().toCharArray().length!=10) {
            contact.setError("Invalid Contact");
            error=true;
        }
        else{
            contact.setError(null);
            error = false;
        }
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        String emailtext = email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!emailtext.matches(emailPattern)) {
            email.setError("Invalid email");
            error=true;
        }else{
            email.setError(null);
            error=false;
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }
}

