package com.example.coffee_cake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_home.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_home newInstance(String param1, String param2) {
        Fragment_home fragment = new Fragment_home();
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
    ListView listDrinks;
    OrderDrinksAdapter adapter;
    ArrayList<OrderDrinks> arrayList;
    ArrayList<Product> products;
    FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listDrinks = (ListView) view.findViewById(R.id.lvDrinkStack);

        db = FirebaseFirestore.getInstance();



        db.collection("/FoodQueue").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task1) {

                arrayList = new ArrayList<>();
                adapter = new OrderDrinksAdapter(getActivity(),R.layout.layout_menu_drinks,arrayList);

                for (DocumentSnapshot data : task1.getResult())
                {

                    String SIZE, SL, TEN;


                    Task<DocumentSnapshot> task2 = data.getDocumentReference("food_name").get();
                    while(!task2.isComplete());

                    String temp = task2.getResult().getReference().getParent().getParent().getId();

                    int SOBAN = Integer.parseInt(temp);

                    SIZE = task2.getResult().getString("SIZE");
                    SL = task2.getResult().getLong("SOLUONG") + "";

                    Task<DocumentSnapshot> task3 = task2.getResult().getDocumentReference("sp_ref_name").get();
                    while(!task3.isComplete());

                    TEN = task3.getResult().getString("TEN");
                    if (task3.getResult().getReference().getParent().getParent().getId().equals("TRASUA"))
                    {

                    }
                    else
                    {
                        arrayList.add(new OrderDrinks(TEN, SIZE, SL, SOBAN));
                    }

                }
                listDrinks.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });



        return view;
    }

}