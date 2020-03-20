package com.example.pete.ui.booking;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pete.R;

public class BookingMain extends Fragment {

    EditText description;
    DatePicker datePicker;
    ListView listView;
    AddressDBHelper addDBHelper;
    BookingDBHelper bookDBHelper;
    Button add,book;
    private String NAME,EMAIL,CONTACT,LOCATION;
    private String USERNAME="peteadamsgc@gmail.com",PASSWORD="Androidapp2020";

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
        book = root.findViewById(R.id.book);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.bookingFragment);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().toString().matches("")){
                    description.setError("Required");
                    return;
                }

                if(listView.getSelectedItem() == null){
                    Toast.makeText(getContext(),"Address Required",Toast.LENGTH_SHORT).show();
                    return;
                }

                description.setError(null);
                try {
                    GMailSender sender = new GMailSender(USERNAME, PASSWORD);
                    sender.sendMail( NAME,
                           "Date : "+datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear()+" \n "+
                                   "Address: "+LOCATION+" \n "+
                                   "Contact: "+CONTACT+" \n "+
                                   "Description: "+description.getText().toString().trim(),
                            USERNAME,
                            EMAIL);
                } catch (Exception e) {
                    Log.e("SendMail ", e.getMessage(), e);
                    return;
                }

                SQLiteDatabase db = bookDBHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(BookingContract.COLUMN_NAME_NAME, NAME);
                values.put(BookingContract.COLUMN_NAME_DATE, datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear());
                values.put(BookingContract.COLUMN_NAME_ADDRESS, LOCATION);
                long newRowId = db.insert(AddressContract.TABLE_NAME, null, values);

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.bookingMain);
                Log.i(this.getClass().getName(),"Written "+ newRowId+" lines into database.");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView name = view.findViewById(R.id.listname);
                TextView address = view.findViewById(R.id.listaddress);
                TextView contact = view.findViewById(R.id.listcontact);
                TextView email = view.findViewById(R.id.listemail);

                NAME=name.getText().toString();
                LOCATION=address.getText().toString();
                EMAIL=email.getText().toString();
                CONTACT=contact.getText().toString();

                Log.i("Selected Item",NAME);
            }});

        updateList();
        return root;
    }

    private void updateList() {
        SQLiteDatabase db = addDBHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AddressContract.COLUMN_NAME_NAME,
                AddressContract.COLUMN_NAME_EMAIL,
                AddressContract.COLUMN_NAME_CONTACT,
                AddressContract.COLUMN_NAME_ADDRESS
        };

        String sortOrder = AddressContract.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                AddressContract.TABLE_NAME,   // The table to query
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
