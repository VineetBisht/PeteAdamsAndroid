package com.example.pete.ui.cost_reduction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pete.R;

import java.util.List;

public class CostReductionAdapter extends RecyclerView.Adapter<CostReductionAdapter.MyView> {
    private List<String> options;
    private List<String> optionDetails;

    public CostReductionAdapter(List<String> options, List<String> optionDetails) {
        this.options = options;
        this.optionDetails=optionDetails;
     }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fix_options, parent
                        , false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position) {
        holder.card.setText(options.get(position));
        holder.cardDetails.setText(optionDetails.get(position));
        switch (position) {
            case 0:
                holder.image.setImageResource(R.mipmap.light);
                break;
            case 1:
                holder.image.setImageResource(R.mipmap.drain);
                break;
            case 2:
                holder.image.setImageResource(R.mipmap.door);
                break;
            case 3:
                holder.image.setImageResource(R.mipmap.tap);
                break;
            case 4:
                holder.image.setImageResource(R.mipmap.closed_door);
                break;
            case 5:
                holder.image.setImageResource(R.mipmap.chat);
                break;

            default: Log.e(CostReductionAdapter.class.getName(),"Image Resource Error");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView card;
        TextView cardDetails;
        ImageView image;

        public MyView(View view) {
            super(view);
            card = (TextView) view.findViewById(R.id.card);
            cardDetails = (TextView) view.findViewById(R.id.cardDetails);
            image = (ImageView) view.findViewById(R.id.option_image);
        }
    }

}
