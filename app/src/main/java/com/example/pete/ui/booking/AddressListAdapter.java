package com.example.pete.ui.booking;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.pete.R;

public class AddressListAdapter extends CursorAdapter {
    public AddressListAdapter(Context context, Cursor cursor) {
        super(context, cursor,0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView listname = (TextView) view.findViewById(R.id.listname);
        TextView listcontact = (TextView) view.findViewById(R.id.listcontact);
        TextView listaddress = (TextView) view.findViewById(R.id.listaddress);
        TextView listemail = (TextView) view.findViewById(R.id.listemail);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COLUMN_NAME_NAME));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COLUMN_NAME_EMAIL));
        String contact = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COLUMN_NAME_CONTACT));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COLUMN_NAME_ADDRESS));
        // Populate fields with extracted properties
        listname.setText(name);
        listcontact.setText(contact);
        listaddress.setText(address);
        listemail.setText(email);
    }
}