package com.example.pete.ui.quick_fixes;

import android.media.Image;
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

public class
QuickFixAdapter extends RecyclerView.Adapter<QuickFixAdapter.MyView> {
    private List<String> options;
    private List<String> optionDetails;
    ImageView direction;

    QuickFixAdapter(List<String> options, List<String> optionDetails, ImageView direction) {
        this.options = options;
        this.optionDetails=optionDetails;
        this.direction=direction;
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
                holder.image.setImageResource(R.mipmap.wires);
                break;
            case 1:
                holder.image.setImageResource(R.mipmap.plunger);
                direction.setImageResource(R.mipmap.bidirectional);
                break;
            case 2:
                direction.setImageResource(R.mipmap.left);
                holder.image.setImageResource(R.mipmap.kitchen);
                break;

            default: Log.e(QuickFixAdapter.class.getName(),"Image Resource Error");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
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
