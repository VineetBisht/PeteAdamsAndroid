package com.example.pete.home.booking;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.pete.R;
import com.example.pete.ui.booking.BookingContract;

public class BookingListAdapter extends CursorAdapter {
    public BookingListAdapter(Context context, Cursor cursor) {
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
        TextView showname = view.findViewById(R.id.showname);
        TextView showposition = view.findViewById(R.id.position);
        TextView showdate = view.findViewById(R.id.showdate);
        TextView showaddress = view.findViewById(R.id.showaddress);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.COLUMN_NAME_NAME));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.COLUMN_NAME_DATE));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.COLUMN_NAME_ADDRESS));
        // Populate fields with extracted properties
        showname.setText(name);
        showaddress.setText(address);
        showdate.setText(date);
        showposition.setText(String.valueOf(cursor.getPosition()+1));
    }
}