package com.example.coffee_cake;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_statistic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_statistic extends Fragment {
    private View mview;
    BarChart barChart;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_statistic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_statistic.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_statistic newInstance(String param1, String param2) {
        Fragment_statistic fragment = new Fragment_statistic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    final Calendar instance = Calendar.getInstance();

    private String[] MOY = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    ArrayList<BarEntry> barEntries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_statistic, container, false);



        barChart = mview.findViewById(R.id.barChart);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis();
       // barChart.getXAxis().setValueFormatter(new Formatter(MOY));
        barChart.getAxisLeft().setSpaceBottom(0);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

//        BarDataSet barDataSet = new BarDataSet(barEntries, "None");

//        barDataSet.setColors(Color.argb( 200,22, 208, 255)); // ocean blue
//        barDataSet.setDrawValues(true);
//        barChart.setData(new BarData(barDataSet));
//        barChart.animateY(3000);

        SetBarchart(2021);

        return mview;
    }

    private void SetBarchart(int Year)
    {
        instance.set(Year, 1, 1, 0, 0, 0);
        long start = instance.getTimeInMillis() / 1000;
        instance.set(Year + 1, 1, 1, 0, 0, 0);
        long end = instance.getTimeInMillis() / 1000;

        FirebaseFirestore.getInstance().collection("HOADON").whereGreaterThanOrEqualTo( "NGHD", start).whereLessThanOrEqualTo("NGHD", end).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                barEntries = new ArrayList<>();
                for (int i = 0; i < 12; i++)
                    barEntries.add(new BarEntry(i + 1, 0));

                for (DocumentSnapshot data : task.getResult())
                {
                   instance.setTimeInMillis(data.getLong("NGHD") * 1000);
                   int index = instance.get(Calendar.MONTH) - 1;
                   barEntries.get(index).setY( barEntries.get(index).getY() + data.getLong("TRIGIA"));
                }

                for (BarEntry entry : barEntries)
                {
                    Log.i (entry.getX() + "", entry.getY() + "");
                }

                BarDataSet dataSet = new BarDataSet(barEntries, "");
                barChart.setData(new BarData(dataSet));
                barChart.animateY(3000);
            }
        });
    }

    class Formatter extends ValueFormatter
    {
        private String[] Labels;

        Formatter(String[] labels) { Labels = labels;}

        @Override
        public String getFormattedValue(float value) {
            return Labels[(int)value];
        }

        @Override
        public String getBarLabel(BarEntry barEntry)
        {
            return getFormattedValue(barEntry.getY());
        }
    }
}