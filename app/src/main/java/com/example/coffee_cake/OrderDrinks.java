package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.Navigation;

import java.util.ArrayList;

public class OrderDrinks {
    private String name,size,soluong, id;
    private ArrayList<Product> topping;
    private int soban;

    public OrderDrinks(String id, String name, String size, String soluong,ArrayList<Product> topping, int soban) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.soluong = soluong;
        this.topping = topping;
        this.soban = soban;
    }
    public OrderDrinks(String name, String size, String soluong,ArrayList<Product> topping, int soban) {
        this.name = name;
        this.size = size;
        this.soluong = soluong;
        this.topping = topping;
        this.soban = soban;
    }
    public OrderDrinks(String id, String name, String size, String soluong,ArrayList<Product> topping) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.soluong = soluong;
        this.topping = topping;
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


    public ArrayList<Product> getTopping() {
        return topping;
    }

    public void setTopping(ArrayList<Product> topping) {
        this.topping = topping;
    }
}

class  OrderDrinksAdapter extends BaseAdapter
{
    TextView tvnametable,tvsoluong,tvtopping,tvtable;
    ImageView daubacham;
    MenuBuilder menuBuilder;
    ViewModel_for_food viewModel_for_food;
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
        return m_array.get(i).getSoban();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { // tên(size), số lượng ,topping, số bàn

        view = LayoutInflater.from(m_Context).inflate(m_Layout,null);

        tvnametable = (TextView) view.findViewById(R.id.tvnametable);
        tvsoluong = (TextView) view.findViewById(R.id.tvsoluong);
        tvtopping = (TextView) view.findViewById(R.id.tvtopping);
        tvtable = (TextView) view.findViewById(R.id.tvtable);
        daubacham = (ImageView)view.findViewById(R.id.imgDetail);

        tvnametable.setText(   m_array.get(i).getName()     );
        viewModel_for_food = new ViewModelProvider(ViewModelStore::new).get(ViewModel_for_food.class);

        if(viewModel_for_food.getQueues() == null)
            Toast.makeText(m_Context, "NULL", Toast.LENGTH_SHORT).show();

        tvsoluong.setText( "Số lượng: "+ m_array.get(i).getSoluong() +"(" +  m_array.get(i).getSize() + ")" );

        if (m_array.get(i).getTopping()==null || m_array.get(i).getTopping().equals(" ")) // ẩn đi textview topping nếu ko phải là trà sữa
            tvtopping.setVisibility(View.INVISIBLE);
        else
            tvtopping.setText( "Topping: " + m_array.get(i).getTopping() );

        tvtable.setText("Bàn: " + m_array.get(i).getSoban());

        daubacham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuBuilder = new MenuBuilder(view.getContext());
                MenuInflater inflater = new MenuInflater(view.getContext());

                inflater.inflate(R.menu.menu_for_orderdrinks, menuBuilder);

                MenuPopupHelper menuPopupHelper = new MenuPopupHelper(view.getContext(), menuBuilder, view);
                menuPopupHelper.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getTitle().equals("Hoàn thành")) {
                            //Toast.makeText(view.getContext(), "Bàn số " + m_array.get(i).getSoban(), Toast.LENGTH_SHORT).show();



                        } else if (item.getTitle().equals("Hủy bỏ")) {
                            m_array.remove(i);
                            viewModel_for_food.setQueues(m_array);


//                            Fragment frg = null;
//                            frg = getSupportFragmentManager().findFragmentByTag("app_name");
//                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                            ft.detach(frg);
//                            ft.attach(frg);
//                            ft.commit();
                        }
                        return true;
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                menuPopupHelper.show();
            }
        });
        return view;
    }
}
