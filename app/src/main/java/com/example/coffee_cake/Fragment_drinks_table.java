package com.example.coffee_cake;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
    private MyVM viewModel;
    TableAdapter adapter;
    File path;
    boolean status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drinks_table, container, false);
        tableList = (GridView) view.findViewById(R.id.tableList);

        String fileName = "table_status.txt";
        viewModel = new ViewModelProvider(requireActivity()).get(MyVM.class);
        table = new ArrayList<>();
        path = getContext().getFilesDir();

        File savedFile = new File(path + "/" + fileName);
        if(!savedFile.exists()){
            for(int j = 0; j < 10; j++){
                Random rd = new Random();
                table.add(rd.nextBoolean());
            }
            writeToFile(fileName);
        }
        readFile(fileName);

        viewModel.setTables(table);
        ((ImageView)view.findViewById(R.id.btnAddTable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.add(false);
                viewModel.setTables(table);

                deleteFile(fileName);
                writeToFile(fileName);
            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Boolean>>() {
            @Override
            public void onChanged(ArrayList<Boolean> booleans) {
                adapter.notifyDataSetChanged();
            }
        });

        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);

                if(table.get(i)){
                    popupMenu.getMenuInflater().inflate(R.menu.menu_for_noempty, popupMenu.getMenu());
                }
                else{
                    popupMenu.getMenuInflater().inflate(R.menu.menu_for_empty, popupMenu.getMenu());
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Tính tiền")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("key1", i+1);
                            bundle.putString("key2", fileName);
                            Navigation.findNavController(view).navigate(R.id.action_menuDrinkTable_to_fragment_bill, bundle);
                        } else if (item.getTitle().equals("Gọi món")) {
                            Navigation.findNavController(view).navigate(R.id.action_fragment_drinks_table_to_fragment_Menu);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        loadTable();
        return view;
    }

    private void readFile(String fileName) {
        try {
            FileReader rdr = new FileReader(path + "/" + fileName);
            table.clear();
            char[] inputBuffer = new char[1024*1024];
            String savedData = "";
            int charRead = rdr.read(inputBuffer);
            int i = 0; //số bàn
            for (int k = 0; k < charRead; k++) {
                savedData += inputBuffer[k];
                if(isStatus(savedData)){
                    table.add(i, status);
                    i++;
                    savedData = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String fileName) {
        File savedFile = new File(path + "/" + fileName);

        if (!savedFile.exists())
            Toast.makeText(getContext(), "Không tồn tại file",
                    Toast.LENGTH_SHORT).show();
        else {
            savedFile.delete();
        }
    }

    private void writeToFile(String fileName) {
        ArrayList<String> s = new ArrayList<>();

        for(int i = 0; i < table.size(); i++){
            s.add(i, table.get(i).toString());
        }

        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            for(int i = 0; i < table.size(); i++){
                writer.write(s.get(i).getBytes());
            }
            writer.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Lỗi file", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isStatus(String s) {
        if(s.equals("true") || s.equals("false")){
            status = (s.equals("true")? true: false);
            return true;
        }
            return false;
    }

    private void loadTable() {
        adapter = new TableAdapter(getContext(), table);
        tableList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}