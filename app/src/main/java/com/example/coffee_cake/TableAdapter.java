package com.example.coffee_cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
            }
            else {
                view = LayoutInflater.from(context).inflate(R.layout.layout_drinks_table_empty, viewGroup, false);
            }
        }
        return view;
    }



}
