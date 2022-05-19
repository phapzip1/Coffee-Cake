package com.example.coffee_cake;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_order_notopping#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_order_notopping extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_order_notopping() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_order_notopping.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_order_notopping newInstance(String param1, String param2) {
        Fragment_order_notopping fragment = new Fragment_order_notopping();
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

    TextView name,soluong,gia,s,m,l;
    Button btnthemngay;
    Boolean bs,bl,bm;
    ImageView add,remove;
    int sl,soban;
    Bundle bund;
    String size = "S", theloai, masp;
    MaterialCardView selectCard;
//    File path;
//    ArrayList<Boolean> table;
//    ArrayList<OrderDrinks> foodOrders;
//    MyVM viewModel;
//    ViewModel_for_food viewModel_for_food;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_notopping, container, false);

        db = FirebaseFirestore.getInstance();

        name = (TextView) v.findViewById(R.id.tvOrdernotopping);
        soluong = (TextView) v.findViewById(R.id.tvQuantitynotopping);

        gia = (TextView) v.findViewById(R.id.tvPricenotopping);

        s = (TextView) v.findViewById(R.id.sizeSnotopping);
        m = (TextView) v.findViewById(R.id.sizeMnotopping);
        l = (TextView) v.findViewById(R.id.sizeLnotopping);

        bund = getArguments(); // lấy giá trị, có số bàn
        soban = bund.getInt("soban");
        masp = bund.getString("MASP");
        theloai = bund.getString("theloai");

        add = (ImageView) v.findViewById(R.id.btnAddnotopping);
        remove = (ImageView) v.findViewById(R.id.btnRemovenotopping);
        sl = 1;
        bs = true;
        bm = false;
        bl = false;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl++;
                soluong.setText(String.valueOf(sl));
                if (bs)
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA")   ) ));
                else if (bm)
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA")   + 5000 ) ));
                else
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA")  + 10000 ) ));

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sl>1)
                {
                    sl--;
                    soluong.setText(String.valueOf(sl));

                    if (bs)
                        gia.setText(String.valueOf(sl*( bund.getInt("GIA")   ) ));
                    else if (bm)
                        gia.setText(String.valueOf(sl*( bund.getInt("GIA")  + 5000 ) ));
                    else
                        gia.setText(String.valueOf(sl*( bund.getInt("GIA")  + 10000 ) ));

                }
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bm==true || bl== true )
                {
                    size = "S";
                    gia.setText(String.valueOf(sl*bund.getInt("GIA") ));
                    bs = true;
                    s.setTextColor(Color.parseColor("#ffffff"));
                    s.setBackgroundResource(R.drawable.round_bg);

                    bm = bl = false;
                    m.setText("M");
                    m.setTextColor(Color.parseColor("#111111"));
                    m.setBackgroundResource(R.drawable.round_bg_white);

                    l.setText("L");
                    l.setTextColor(Color.parseColor("#000000"));
                    l.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bs==true || bl== true )
                {
                    size = "M";
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 5000  ) ));
                    bm = true;
                    m.setTextColor(Color.parseColor("#ffffff"));
                    m.setBackgroundResource(R.drawable.round_bg);


                    bs = bl = false;
                    s.setText("S");
                    s.setTextColor(Color.parseColor("#111111"));
                    s.setBackgroundResource(R.drawable.round_bg_white);

                    l.setText("L");
                    l.setTextColor(Color.parseColor("#000000"));
                    l.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bm==true || bs== true )
                {
                    size = "L";
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 10000  ) ));
                    bl = true;
                    l.setTextColor(Color.parseColor("#ffffff"));
                    l.setBackgroundResource(R.drawable.round_bg);

                    bm = bs = false;
                    m.setText("M");
                    m.setTextColor(Color.parseColor("#111111"));
                    m.setBackgroundResource(R.drawable.round_bg_white);

                    s.setText("S");
                    s.setTextColor(Color.parseColor("#000000"));
                    s.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });

        name.setText(bund.getString("TENSP"));
        gia.setText(String.valueOf(bund.getInt("GIA")));

        btnthemngay = (Button) v.findViewById(R.id.btnOrderNownotopping);
        btnthemngay.setOnClickListener(new View.OnClickListener() { // tên(size), số lượng ,topping, số bàn
            @Override
            public void onClick(View view) {
                changeTableStatus(soban);

                saveFoodOrderIntoAFile();

                Navigation.findNavController(view).navigate(R.id.action_fragment_order_notopping_to_menuHome);
            }
        });

        return v;
    }

    private void saveFoodOrderIntoAFile() {
        Map<String, Object> map = new HashMap<>();
        map.put("sp_ref_name", db.document(theloai + '/' + masp));
        map.put("SIZE", size);
        map.put("SOLUONG", Long.parseLong(soluong.getText().toString()));

        String format;
        if(soban+1 < 10) format = "0"+ (soban+1);
        else format = (soban+1) + "";

        db.collection("/TableStatus/" + format + "/DrinksOrder").add(map)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Map<String, Object> queue = new HashMap<>();
                queue.put("food_name", db.document("/TableStatus/" + format + "/DrinksOrder/" + task.getResult().getId()));
                db.collection("/FoodQueue").add(queue);
            }
        });
    }

    private void changeTableStatus(int soban) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        String format;
        if(soban+1 < 10) format = "0"+ (soban+1);
        else format = (soban+1) + "";

        db.collection("/TableStatus").document(format).set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


}