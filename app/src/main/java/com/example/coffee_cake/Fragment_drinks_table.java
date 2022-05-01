package com.example.coffee_cake;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

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

    GridView tableList;
    ArrayList<Boolean> table;
    boolean trangThai;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks_table, container, false);
        tableList = (GridView) view.findViewById(R.id.tableList);
        loadTable();
        registerForContextMenu(tableList);
        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trangThai = table.get(i);
                Toast.makeText(getContext(), "ạubgoaigbo", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        if(trangThai){
            Toast.makeText(getContext(), "6666666", Toast.LENGTH_SHORT).show();
            inflater.inflate(R.menu.menu_for_noempty, menu);
        }
        else{
            Toast.makeText(getContext(), "777777777", Toast.LENGTH_SHORT).show();
            inflater.inflate(R.menu.menu_for_empty, menu);
        }
    }

    // menu item select listener
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Tính tiền") {
            Toast.makeText(getContext(), "Tính toán", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Gọi món") {
            Toast.makeText(getContext(), "Gọi món", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void loadTable() {
        Random rd = new Random();
        table = new ArrayList<>();
        for(int j = 0; j < 10; j++){
            table.add(rd.nextBoolean());
        }
        TableAdapter adapter = new TableAdapter(getContext(), table);
        tableList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}