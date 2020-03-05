package com.example.pete.ui.home;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import com.example.pete.R;
import java.util.List;

public class HomeAdapter extends PagerAdapter {
    private List<String> options;
    Context context;
    OnPagerListener onPagerListener;

    HomeAdapter(List<String> options,  Context context, OnPagerListener onPagerListener) {
        this.options = options;
        this.context = context;
        this.onPagerListener=onPagerListener;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.main_options, container
                        , false);

        TextView card = itemView.findViewById(R.id.card);
        final int positionInside=position;
        ImageView image = itemView.findViewById(R.id.option_image);

        itemView.findViewById(R.id.cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("here","working");
                onPagerListener.onPagerClick(positionInside);

            }
        });

        card.setText(options.get(position));
        switch (options.get(position)) {
            case "Quick Fixes":
                image.setImageResource(R.mipmap.fix);
                break;
            case "Cost Reduction":
                image.setImageResource(R.mipmap.money);
                break;
            case "New Booking":
                image.setImageResource(R.mipmap.calendar);
                break;

            default:
                Log.e(HomeAdapter.class.getName(), "Image Resource Error");
                break;
        }
        container.addView(itemView, 0);
        return itemView;
    }

    public interface OnPagerListener{
        void onPagerClick(int position);
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((CardView)object);
    }
}

