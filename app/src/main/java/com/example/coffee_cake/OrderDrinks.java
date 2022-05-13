package com.example.coffee_cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDrinks {
    private String name,size,soluong,topping;
    private int soban;

    public OrderDrinks(String name, String size, String soluong,String topping, int soban) {
        this.name = name;
        this.size = size;
        this.soluong = soluong;
        this.topping = topping;
        this.soban = soban;
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

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
}

class  OrderDrinksAdapter extends BaseAdapter
{
    TextView tvnametable,tvsoluong,tvtopping,tvtable;
    private Context m_Context;
    private ArrayList<OrderDrinks> m_array;
    private int m_Layout;
    public OrderDrinksAdapter(Context context, int layout, ArrayList<OrderDrinks> arrayList)
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
    public View getView(int i, View view, ViewGroup viewGroup) { // tên(size), số lượng ,topping, số bàn

        view = LayoutInflater.from(m_Context).inflate(m_Layout,null);

        tvnametable = (TextView) view.findViewById(R.id.tvnametable);
        tvsoluong = (TextView) view.findViewById(R.id.tvsoluong);
        tvtopping = (TextView) view.findViewById(R.id.tvtopping);
        tvtable = (TextView) view.findViewById(R.id.tvtable);

        tvnametable.setText(   m_array.get(i).getName()     );

        tvsoluong.setText( "Số lượng: "+ m_array.get(i).getSoluong() +"(" +  m_array.get(i).getSize() + ")" );

        if (m_array.get(i).getTopping()==null) // tr
            tvtopping.setVisibility(View.INVISIBLE);
        else
            tvtopping.setText( "Topping: " + m_array.get(i).getTopping() );

        tvtable.setText("Bàn: " + m_array.get(i).getSoban());

        return view;
    }
}
