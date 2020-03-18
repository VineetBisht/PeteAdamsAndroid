package com.example.pete.ui.booking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pete.R;

import java.util.ArrayList;
import java.util.List;

public class BookingMain extends Fragment {

    EditText description;
    DatePicker datePicker;
    ListView listView;
    BookingDBHelper dbHelper;
    Button add;

    public BookingMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_booking_main, container, false);
        description = root.findViewById(R.id.description);
        datePicker = root.findViewById(R.id.datePicker);
        listView = root.findViewById(R.id.addresses);
        add = root.findViewById(R.id.add_address);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.bookingFragment);
            }
        });

        updateList();
        return root;
    }

    private void updateList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Contract.COLUMN_NAME_NAME,
                Contract.COLUMN_NAME_EMAIL,
                Contract.COLUMN_NAME_CONTACT,
                Contract.COLUMN_NAME_ADDRESS
        };

        String sortOrder = Contract.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                Contract.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        AddressListAdapter addressAdapter = new AddressListAdapter(this.getContext(), cursor);

        // Attach cursor adapter to the ListView
        listView.setAdapter(addressAdapter);
        cursor.close();
    }
}
