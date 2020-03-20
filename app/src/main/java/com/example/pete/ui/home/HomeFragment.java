package com.example.pete.ui.home;

import android.animation.ArgbEvaluator;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.pete.R;
import com.example.pete.ui.booking.BookingContract;
import com.example.pete.ui.booking.BookingDBHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeAdapter.OnPagerListener{

    ViewPager viewPager;
    LinearLayout sliderDots;
    ArgbEvaluator argbEvaluator;
    ListView bookinglist;
    private int dots_count;
    private ImageView[] dots;
    BookingDBHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        argbEvaluator = new ArgbEvaluator();

        viewPager = root.findViewById(R.id.viewPager);
        bookinglist = root.findViewById(R.id.bookinglist);

        LinearLayoutManager horizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HomeAdapter adapter = new HomeAdapter(getOptions(), getContext(),this);

        viewPager.setAdapter(adapter);

        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPadding(20, 0, 20, 0);

        sliderDots = root.findViewById(R.id.sliderDots);
        dots_count=adapter.getCount();
        dots = new ImageView[dots_count];
        for(int i=0;i<dots_count;i++){
            dots[i]=new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));

        final Integer[] colors = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
        };

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ConstraintLayout mainPage= root.findViewById(R.id.mainPage);

                if (position < (dots_count - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );

                    mainPage.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                    mainPage.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for(ImageView i:dots){
                i.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return root;
    }

    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Quick Fixes");
        options.add("Cost Reduction");
        options.add("New Booking");
        return options;
    }

    private void updateList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                BookingContract.COLUMN_NAME_NAME,
                BookingContract.COLUMN_NAME_DATE,
                BookingContract.COLUMN_NAME_ADDRESS
        };

        String sortOrder = BookingContract.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                BookingContract.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        com.example.pete.home.booking.BookingListAdapter addressAdapter = new com.example.pete.home.booking.BookingListAdapter(this.getContext(), cursor);

        // Attach cursor adapter to the ListView
        bookinglist.setAdapter(addressAdapter);
        cursor.close();
    }

    @Override
    public void onPagerClick(int position) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (position){
                    case 0:
                        navController.navigate(R.id.quickFixFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.costReductionFragment);
                        break;
                    case 2:
                        navController.navigate(R.id.bookingMain);
                        break;
                }
    }
}