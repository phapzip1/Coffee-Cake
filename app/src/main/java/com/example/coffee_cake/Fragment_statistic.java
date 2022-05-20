package com.example.coffee_cake;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    private String[] MOY = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

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
        ArrayList<BarEntry> barEntries = new ArrayList<>();







        BarDataSet barDataSet = new BarDataSet(barEntries, "None");
        barDataSet.setColors(Color.argb( 200,22, 208, 255)); // ocean blue
        barDataSet.setDrawValues(true);

        //barChart.invalidate();
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(3000);


        return mview;
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