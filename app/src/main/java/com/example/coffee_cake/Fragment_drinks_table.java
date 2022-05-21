package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_drinks_table#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_drinks_table extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_drinks_table() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_drinks_table.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_drinks_table newInstance(String param1, String param2) {
        Fragment_drinks_table fragment = new Fragment_drinks_table();
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
    TextView textLoadTable;
    ProgressBar loadingTable;
    GridView tableList;
    ArrayList<Boolean> table;
    TableAdapter adapter;
    MenuBuilder menuBuilder;

    FirebaseFirestore db;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drinks_table, container, false);
        tableList = (GridView) view.findViewById(R.id.tableList);
        db = FirebaseFirestore.getInstance();

        //Lưu trạng thái
        db.collection("/TableStatus").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    table = new ArrayList<>();

                    adapter = new TableAdapter(getContext(), table);
                    tableList.setAdapter(adapter);
                    for (QueryDocumentSnapshot data : task.getResult()) {
                        boolean flat = false;
                        Task<QuerySnapshot> task1 = data.getReference().collection("DrinksOrder").get();

                        while(!task1.isComplete());

                        for(DocumentSnapshot dataaa : task1.getResult()){
                            flat = true;
                            break;
                        }
                        table.add(flat);
                        adapter.notifyDataSetChanged();
                    }


                }
            }
        });

        ((ImageView)view.findViewById(R.id.btnAddTable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("status", false);

                String format;
                if(table.size() + 1 >= 10) format = (table.size() + 1) + "";
                else format =  "0"+ (table.size() + 1);

                //Lưu trạng thái
                db.collection("/TableStatus").document( format).set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                //need to reload again
//                getFragmentManager().beginTransaction().detach(Fragment_drinks_table.this).attach(Fragment_drinks_table.this).commit();
                reloadPage();
            }
        });


        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuBuilder = new MenuBuilder(getContext());
                MenuInflater inflater = new MenuInflater(getContext());

                if(table.get(i)){
                    inflater.inflate(R.menu.menu_for_noempty, menuBuilder);
                }
                else{
                    inflater.inflate(R.menu.menu_for_empty, menuBuilder);
                }

                MenuPopupHelper menuPopupHelper = new MenuPopupHelper(getContext(), menuBuilder, view);
                menuPopupHelper.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getTitle().equals("Tính tiền")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("key1", i);
                            Navigation.findNavController(view).navigate(R.id.action_menuDrinkTable_to_fragment_bill, bundle);
                        } else if (item.getTitle().equals("Gọi món")) {
                            Bundle bund = new Bundle();
                            bund.putInt("soban",i);
                            Navigation.findNavController(view).navigate(R.id.action_fragment_drinks_table_to_fragment_Menu,bund);
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

<<<<<<< HEAD
=======
        // set thời gian hiện thị loading
        textLoadTable = view.findViewById(R.id.textloadTable);
        loadingTable = view.findViewById(R.id.loadingTable);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loadingTable.setVisibility(view.INVISIBLE);
                textLoadTable.setVisibility(view.INVISIBLE);

            }
        },3000);
>>>>>>> 670d2889342e5ded9fa062eabb3bf17022f0929f

        return view;
    }

    private void reloadPage() {
        Fragment frg = null;
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("fragment_drinks_table");

        frg = getParentFragmentManager().findFragmentByTag("fragment_drinks_table");
        final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }


}