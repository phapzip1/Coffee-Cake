package com.example.coffee_cake;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_bill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_bill extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_bill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_bill.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_bill newInstance(String param1, String param2) {
        Fragment_bill fragment = new Fragment_bill();
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

    FirebaseFirestore db;
    ListView listFood;
    Bill_adapter adapter;
    ArrayList<OrderDrinks> arrayList;
    TextView overal;
    int soban;
    ArrayList<String> ref_topping;
    int sum = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        db = FirebaseFirestore.getInstance();
        listFood = view.findViewById(R.id.food_item);
        overal = view.findViewById(R.id.tien);

        overal.setText("0đ");
        getDateTime(view);
        getTableNumber(view);

        loadFoodIntoBill();

        ((ImageView) view.findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_bill_to_menuDrinkTable);
            }
        });

        ((Button) view.findViewById(R.id.btnPay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayDialog(Gravity.CENTER, view);
            }
        });


        return view;
    }

    private void loadFoodIntoBill() {
        String format;
        if(soban < 10) format = "0"+ (soban);
        else format = soban + "";

        db.collection("TableStatus/" + format + "/DrinksOrder").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                arrayList = new ArrayList<>();
                ref_topping = new ArrayList<>();
                adapter = new Bill_adapter(getActivity(),R.layout.layout_bill,arrayList);
                //int sum = 0;
                for(DocumentSnapshot data : task1.getResult()){
                    String size, ten, masp, tensp;
                    long gia, GIA;
                    int sl;
                    if(data.getBoolean("DONE")){
                        size = data.getString("SIZE");
                        sl = Integer.parseInt(data.getLong("SOLUONG") + "");
                        GIA = data.getLong("GIA");
                        sum += GIA;

                        Task<DocumentSnapshot> task2 = data.getDocumentReference("sp_ref_name").get();
                        while(!task2.isComplete());

                        ten = task2.getResult().getString("TEN");
                        if (task2.getResult().getReference().getParent().getParent().getId().equals("TRASUA")) {

                            Task<QuerySnapshot> task3 = data.getReference().collection("Topping").get();
                            while(!task3.isComplete());
                            ArrayList<Product> topping = new ArrayList<>();

                            for(DocumentSnapshot dataaaa : task3.getResult()){
                                Task<DocumentSnapshot> task5 = dataaaa.getDocumentReference("topping_ref").get();
                                while(!task5.isComplete());

                                masp = task5.getResult().getReference().getId();
                                tensp = task5.getResult().getString("TEN");
                                gia = task5.getResult().getLong("GIA");

                                ref_topping.add(masp);
                                topping.add(new Product(masp, tensp, Integer.parseInt(gia + "")));
                            }
                            arrayList.add(new OrderDrinks(data.getId(),ten, size, sl+"", topping, soban, (int)GIA));

                        }
                        else {
                            arrayList.add(new OrderDrinks(data.getId(),ten, size, sl+ "", soban, (int)GIA));
                        }
                        overal.setText(sum + " đ");
                    }
                }
                listFood.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void openPayDialog(int gravity, View view) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_payment);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else dialog.setCancelable(false);

        ((Button)dialog.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ((Button)dialog.findViewById(R.id.btnThanhToan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String format;
                if(bundle.getInt("key1")+1 < 10) format = "0"+ (bundle.getInt("key1")+1);
                else format = (bundle.getInt("key1")+1) + "";

                String dateTime;
                Calendar calendar;
                SimpleDateFormat simpleDateFormat;

                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("yyyy-LL-dd");
                dateTime = simpleDateFormat.format(calendar.getTime());

                Map<String, Object> map = new HashMap<>();
                map.put("NGHD", dateTime);
                map.put("TRIGIA", sum);
                map.put("TIME", new Timestamp(new Date()));

                db.collection("HOADON").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        db.collection("TableStatus/" + format + "/DrinksOrder").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                        for (DocumentSnapshot data : task1.getResult()){
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("MASP", data.getDocumentReference("sp_ref_name"));
                                            map.put("SL", data.getLong("SOLUONG"));
                                            map.put("GIA", data.getLong("GIA"));
                                            map.put("SIZE", data.getString("SIZE"));

                                            db.collection(task.getResult().getPath() + "/CTHD").add(map)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task3) {
                                                    data.getReference().collection("Topping").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                                    for(DocumentSnapshot dataa : task2.getResult()){
                                                                        Map<String, Object> map = new HashMap<>();
                                                                        map.put("MATOPPING", dataa.getDocumentReference("topping_ref"));

                                                                        db.collection(task3.getResult().getPath() + "/TOPPING").add(map)
                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                        dataa.getReference().delete();
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                }
                                            });
                                            data.getReference().delete();
                                        }
                                    }
                                });
                    }
                });

//                db.collection("TableStatus/" + format + "/DrinksOrder").get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                for(DocumentSnapshot data : task.getResult()){
//                                    if(data.getBoolean("DONE")){
//                                        data.getReference().delete();
//
//                                        data.getReference().collection("Topping").get()
//                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
//                                                        for (DocumentSnapshot dataa : task2.getResult()) dataa.getReference().delete();
//                                                    }
//                                                });
//                                    }
//                                }
//                            }
//                        });

                Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_fragment_bill_to_menuDrinkTable);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getTableNumber(View view) {
        Bundle bundle = getArguments();
        soban = bundle.getInt("key1") + 1;
        ((TextView)view.findViewById(R.id.table_number)).setText("Bàn " + (bundle.getInt("key1") + 1));
    }

    private void getDateTime(View view) {
        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm EEEE, dd LLLL yyyy");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        ((TextView) view.findViewById(R.id.timeDate)).setText(dateTime);
    }
}