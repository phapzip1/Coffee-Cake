package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_menu_coffee_notable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_menu_coffee_notable extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_menu_coffee_notable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_menu_coffee_notable.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_menu_coffee_notable newInstance(String param1, String param2) {
        Fragment_menu_coffee_notable fragment = new Fragment_menu_coffee_notable();
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


    ProductAdapterUpdate adapter;
    ArrayList<Product> arrayList;
    ListView lvcoffeeno;
    EditText edtcoffeeno;
    String tam;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_coffee_notable, container, false);



        DBHelper db = new DBHelper(getActivity());
        edtcoffeeno = (EditText) v.findViewById(R.id.edtcoffeeno);
        lvcoffeeno = (ListView) v.findViewById(R.id.lvcoffeeno);

        arrayList = new ArrayList<>();
        adapter = new ProductAdapterUpdate(getActivity(),R.layout.layout_menu_drinks_notable_update,arrayList);
        lvcoffeeno.setAdapter(adapter);
        arrayList.clear();


        Bundle bundle = getArguments(); // có cái temp: tức là chọn vào cái nào của menu và số bàn
        tam = bundle.getString("temp");

        Cursor cursor = null;

        switch (tam)
        {
            case "coffee":
                cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'CA%' ",null);
                break;
            case "trasua":
                cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'TS%' ",null);
                break;
            case "sinhto":
                cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'ST%' ",null);
                break;
            case "topping":
                cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'TO%' ",null);
                break;
        }
        while(cursor.moveToNext())
        {
            String MASP = cursor.getString(cursor.getColumnIndex("MASP"));
            String TENSP = cursor.getString(cursor.getColumnIndex("TENSP"));
            int GIA = cursor.getInt(cursor.getColumnIndex("GIA"));
            Product temp = new Product(MASP,TENSP,GIA);
            arrayList.add(temp);
        }
        edtcoffeeno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'CA%' ",null);
                arrayList.clear();
                while(cursor.moveToNext())
                {
                    String TENSP = cursor.getString(cursor.getColumnIndex("TENSP"));
                    if (TENSP.contains(edtcoffeeno.getText().toString()))
                    {
                        String MASP = cursor.getString(cursor.getColumnIndex("MASP"));
                        int GIA = cursor.getInt(cursor.getColumnIndex("GIA"));
                        Product temp = new Product(MASP,TENSP,GIA);
                        arrayList.add(temp);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        adapter.notifyDataSetChanged();

        //hue
        lvcoffeeno.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle1 = new Bundle();
            bundle1.putString("MASP", arrayList.get(i).getMasp());
            bundle1.putString("TenSP", arrayList.get(i).getTensp());
            bundle1.putInt("Gia", arrayList.get(i).getGia());

            Navigation.findNavController(view).navigate(R.id.action_fragment_menu_coffee_notable2_to_fragment_drinks_info, bundle1);
        });

        ((ImageView)v.findViewById(R.id.btnAddDrink)).setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_menu_coffee_notable2_to_fragment_drinks_edit);
        });



        return v;
    }
}