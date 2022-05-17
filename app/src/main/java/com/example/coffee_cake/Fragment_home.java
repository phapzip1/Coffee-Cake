package com.example.coffee_cake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
    ViewModel_for_food viewModel_for_food;
    File path;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        path = getContext().getFilesDir();
        viewModel_for_food = new ViewModelProvider(requireActivity()).get(ViewModel_for_food.class);

        listDrinks = (ListView) view.findViewById(R.id.lvDrinkStack);
        arrayList = new ArrayList<>();

        adapter = new OrderDrinksAdapter(getActivity(),R.layout.layout_menu_drinks,arrayList);

        File savedFile = new File(path + "/" + "FoodQueue.txt");
        if(savedFile.exists()){
            readFile("FoodQueue.txt", view);
            viewModel_for_food.setQueues(arrayList);
        }

        return view;
    }

    String name, size, soluong, topping = "";
    int soban;
    private void readFile(String s, View view) {
        try {
            FileReader rdr = new FileReader(path + "/" + s);
            arrayList.clear();
            char[] inputBuffer = new char[1024*1024];
            String savedData = "";
            int charRead = rdr.read(inputBuffer);
            int i1 = 0; //stt món
            for (int k = 0; k < charRead; k++) {
                savedData += inputBuffer[k];
                if(inputBuffer[k+1] == '/' || inputBuffer[k+1] == '\n'){
                    switch (i1){
                        case 0:
                            name = savedData;
                            break;
                        case 1:
                            size = savedData;
                            break;
                        case 2:
                            soluong = savedData;
                            break;
                        case 3:
                            topping = savedData;
                            break;
                        case 4:
                            soban = Integer.parseInt(savedData);
                            break;
                    }
                    i1++;
                    k++;
                    if(i1 == 5){
                        //OrderDrinks temp = new OrderDrinks(name,size,soluong,topping, soban);

//                        arrayList.add(temp);
//                        listDrinks.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                        i1 = 0;
                    }
                    savedData = "";
                }

            } }catch (Exception e) {
            e.printStackTrace();
        }

    }
}