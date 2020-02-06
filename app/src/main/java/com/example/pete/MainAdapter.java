package com.example.pete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyView> {
    private List<String> options;

    MainAdapter(List<String> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_options, parent
                        , false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position) {
        holder.card.setText(options.get(position));
        switch (options.get(position)) {
            case "Quick Fixes":
                holder.image.setImageResource(R.mipmap.fix);
                break;
            case "Cost Reduction":
                holder.image.setImageResource(R.mipmap.money);
                break;
            case "New Booking":
                holder.image.setImageResource(R.mipmap.calendar);
                break;

                default: Log.e(MainAdapter.class.getName(),"Image Resource Error");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView card;
        ImageView image;

        public MyView(View view) {
            super(view);
            card = (TextView) view.findViewById(R.id.card);
            image = (ImageView) view.findViewById(R.id.option_image);
        }
    }

}
