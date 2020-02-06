package com.example.pete.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;

import java.util.HashMap;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyView> {
    private HashMap<Integer,String> options;

    DateAdapter(HashMap<Integer,String> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_options, parent
                        , false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position) {
        int pos=position+1;
        holder.date_no.setText(String.valueOf(pos));
        holder.date.setText(options.get(pos));
        Log.i(DateAdapter.class.getName(),position+": "+options.get(position)+"\n");
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView date_no,date;

        public MyView(View view) {
            super(view);
            date_no = (TextView) view.findViewById(R.id.date_no);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

}
