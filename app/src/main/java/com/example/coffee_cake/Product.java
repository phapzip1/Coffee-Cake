package com.example.coffee_cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Product {
    private String masp,tensp;
    private int gia;

    public Product(String masp, String tensp, int gia) {
        this.masp = masp;
        this.tensp = tensp;
        this.gia = gia;
    }
    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}

class ProductAdapter extends BaseAdapter
{
    TextView tvname,tvmasp,tvprice;
    private Context m_Context;
    private ArrayList<Product> m_array;
    private int m_Layout;
    public ProductAdapter(Context context, int layout, ArrayList<Product> arrayList)
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
    public View getView(int i, View view, ViewGroup viewGroup) { // chưa
        view = LayoutInflater.from(m_Context).inflate(m_Layout,null);
        tvname = (TextView) view.findViewById(R.id.tvname);
        tvmasp = (TextView) view.findViewById(R.id.tvmasp);
        tvprice = (TextView) view.findViewById(R.id.tvprice);

        tvname.setText(m_array.get(i).getTensp());
        tvmasp.setText(m_array.get(i).getMasp());
        tvprice.setText(m_array.get(i).getGia()+"");
        return view;
    }
}

