package com.example.coffee_cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class OrderDrinks {
    private String name,size,soluong;
    private int soban;

    public OrderDrinks(String name, String size, String soluong) {
        this.name = name;
        this.size = size;
        this.soluong = soluong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getSoban() {
        return soban;
    }

    public void setSoban(int soban) {
        this.soban = soban;
    }
}

class AdapterOrderDrinks extends BaseAdapter
{

    private Context m_Context;
    private ArrayList<Product> m_array;
    private int m_Layout;
    public AdapterOrderDrinks(Context context, int layout, ArrayList<Product> arrayList)
    {
        m_Context = context;
        m_Layout = layout;
        m_array = arrayList;
    }
    @Override
    public int getCount() {
        return m_array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(m_Context).inflate(m_Layout,null);





        return view;
    }
}
