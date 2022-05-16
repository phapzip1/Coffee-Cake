package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_menu_coffee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_menu_coffee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_menu_coffee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_menu_coffee.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_menu_coffee newInstance(String param1, String param2) {
        Fragment_menu_coffee fragment = new Fragment_menu_coffee();
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

    ProductAdapter adapter;
    ArrayList<Product> arrayList;
    ListView lvcoffee;
    EditText edtsearch;
    String tam;
    int soban;
    //String xacnhan = "";
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_coffee, container, false);

        DBHelper db = new DBHelper(getActivity());
        edtsearch = (EditText) v.findViewById(R.id.edtcoffee);
        lvcoffee = (ListView) v.findViewById(R.id.lvcoffee);




        Bundle bundle = getArguments(); // có cái temp: tức là chọn vào cái nào của menu và số bàn
        tam = bundle.getString("temp");
        soban = bundle.getInt("soban");
        String fileName = bundle.getString("fileName"),
                query = "";


        switch (tam)
        {
            case "coffee":
                query = "/SANPHAM/CAPHE/DANHSACHCAPHE";
                break;
            case "trasua":
                query = "/SANPHAM/TRASUA/DANHSACHTRASUA";
                break;
            case "sinhto":
                query = "/SANPHAM/SINHTO/DANHSACHSINHTO";
                break;
            case "topping":
                query = "/SANPHAM/TOPPING/DANHSACHTOPPING";
                break;
        }

        FirebaseFirestore.getInstance().collection(query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    arrayList = new ArrayList<>();
                    adapter = new ProductAdapter(getActivity(),R.layout.layout_menu_drinks_notable,arrayList);
                    lvcoffee.setAdapter(adapter);

                    for (QueryDocumentSnapshot data : task.getResult())
                    {
                        String MASP = data.getId(),
                                TENSP = data.getString("TEN");
                        Long GIA = (Long)data.get("GIA");

                        arrayList.add(new Product(MASP, TENSP, GIA.intValue()));
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

        edtsearch.addTextChangedListener(new TextWatcher() {
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
                        if (TENSP.contains(edtsearch.getText().toString()))
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

        lvcoffee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bund = new Bundle();

                String Masp = arrayList.get(i).getMasp();
                String Tensp = arrayList.get(i).getTensp();
                int Gia = arrayList.get(i).getGia();
                bund.putString("MASP",Masp);
                bund.putString("TENSP",Tensp);
                bund.putInt("GIA",Gia);
                bund.putInt("soban",soban);
                bund.putString("fileName", fileName);
                if (Masp.contains("TS")) // contains: chứa chuỗi
                    Navigation.findNavController(view).navigate(R.id.action_fragment_menu_coffee_to_fragment_order,bund);
                else
                    Navigation.findNavController(view).navigate(R.id.action_fragment_menu_coffee_to_fragment_order_notopping,bund);
            }
        });

        return v;
    }
}