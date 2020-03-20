package com.example.pete.ui.booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pete.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class BookingFragment extends Fragment implements View.OnKeyListener, OnMapReadyCallback {

    private String KEY="saved";

    private GoogleMap gmap;
    EditText name, contact, email;
    AutoCompleteTextView address;
    Button save;
    MapView mapView;
    boolean error;
    private AddressDBHelper dbHelper;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_booking, container, false);
        error = false;
        PhoneNumberFormattingTextWatcher contactWatcher = new PhoneNumberFormattingTextWatcher();
        name = root.findViewById(R.id.name);
        address = root.findViewById(R.id.address);
        contact = root.findViewById(R.id.contact);
        email = root.findViewById(R.id.email);
        mapView = root.findViewById(R.id.mapView);
        name.setOnKeyListener(this);
        contact.setOnKeyListener(this);
        email.setOnKeyListener(this);
        address.setOnKeyListener(this);
        save = root.findViewById(R.id.save);

        dbHelper = new AddressDBHelper(getContext());

        if(mapView!=null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        address.setAdapter(new PlaceAutoSuggestAdapter(getContext(), android.R.layout.simple_list_item_1));
        address.setOnKeyListener(new View.OnKeyListener() {
                                     @Override
                                     public boolean onKey(View v, int keyCode, KeyEvent event) {

                                         TextView view = (TextView) v;
                                         if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                                 (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                             Double addLoc[] = getLatitudeAndLongitude(view.getText().toString());
                                             LatLng ny = new LatLng(addLoc[0], addLoc[1]);
                                             gmap.addMarker(new MarkerOptions().position(ny)).setTitle(view.getText().toString());
                                             gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
                                             InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                             in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                         }
                                         return false;
                                     }
                                 });

        contact.addTextChangedListener(contactWatcher);
        if (contact.getText().toString().toCharArray().length != 10) {
            contact.setError("Invalid Contact");
            error = true;
        } else {
            contact.setError(null);
            error = false;
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(error==false){
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(AddressContract.COLUMN_NAME_NAME, name.getText().toString());
                values.put(AddressContract.COLUMN_NAME_EMAIL, email.getText().toString());
                values.put(AddressContract.COLUMN_NAME_CONTACT, contact.getText().toString());
                values.put(AddressContract.COLUMN_NAME_ADDRESS, address.getText().toString());
                long newRowId = db.insert(AddressContract.TABLE_NAME, null, values);

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.bookingMain);
                Log.i(this.getClass().getName(),"Written "+ newRowId+" lines into database.");
            }
            }
        });

        String emailtext = email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!emailtext.matches(emailPattern)) {
            email.setError("Invalid email");
            error = true;
        } else {
            email.setError(null);
            error = false;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        gmap = googleMap;
        gmap.setMinZoomPreference(15);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    private Double[] getLatitudeAndLongitude(String address) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;
        double latitude = 0, longitude = 0;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            Address addr = addresses.get(0);
            latitude = addr.getLatitude();
            longitude = addr.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Double[]{latitude, longitude};
    }
}

