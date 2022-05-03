package com.example.coffee_cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TableAdapter extends BaseAdapter {
    private ArrayList<Boolean> table;
    private Context context;

    public TableAdapter() {
    }

    public TableAdapter(Context context, ArrayList<Boolean> table) {
        this.context = context;
        this.table = table;
    }

    @Override
    public int getCount() {
        return table.size();
    }

    @Override
    public Object getItem(int i) {
        return table.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        for(int j = 0; j < 10; j++){
            if(table.get(i)){

                view = LayoutInflater.from(context).inflate(R.layout.layout_drinks_table, viewGroup, false);
                ((TextView)view.findViewById(R.id.title1)).setText("Bàn " + (i+1));
            }
            else {
                view = LayoutInflater.from(context).inflate(R.layout.layout_drinks_table_empty, viewGroup, false);
                ((TextView)view.findViewById(R.id.title2)).setText("Bàn " + (i+1));
            }
        }
        return view;
    }



}
