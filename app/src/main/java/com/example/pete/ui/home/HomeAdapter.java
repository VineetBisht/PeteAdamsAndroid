package com.example.pete.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;
import com.example.pete.ui.quick_fixes.QuickFixAdapter;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyView> {
    private List<String> options;
    OptionListener mOnCardListener;

    HomeAdapter(List<String> options, OptionListener optionClick) {
        this.options = options;
        mOnCardListener=optionClick;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_options, parent
                        , false);

        return new MyView(itemView,mOnCardListener);
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

            default: Log.e(HomeAdapter.class.getName(),"Image Resource Error");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView card;
        ImageView image;
        OptionListener option;

        public MyView(View view, OptionListener option) {
            super(view);
            card = (TextView) view.findViewById(R.id.card);
            image = (ImageView) view.findViewById(R.id.option_image);
            view.setOnClickListener(this);
            this.option=option;
        }

        @Override
        public void onClick(View view) {
            option.onOptionClick(getAdapterPosition());
        }

    }

    public interface OptionListener{
        void onOptionClick(int position);
    }
}
