package com.example.coffee_cake;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Menu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Menu newInstance(String param1, String param2) {
        Fragment_Menu fragment = new Fragment_Menu();
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

    CardView coffee,trasua,sinhto,topping;
    Bundle bundletable;
    String tableId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__menu, container, false);
        // Inflate the layout for this fragment
        coffee = (CardView) v.findViewById(R.id.cvCoffee);
        trasua = (CardView) v.findViewById(R.id.cvTraSua);
        sinhto = (CardView) v.findViewById(R.id.cvSinhTo);
        topping = (CardView) v.findViewById(R.id.cvTopping);

        if (getArguments()!=null) // tức là có bundle được chuyển qua
        //topping.setVisibility(View.INVISIBLE); // ẩn topping
        bundletable = getArguments(); // = NULL: nếu đi từ home -> menu, !=NULL: nếu có giá trị table -> menu
        Bundle bundle = new Bundle();
        if (bundletable!=null)
            tableId = bundletable.getString("soban");

         // số bàn ở đây
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundletable.putString("temp","coffee");
                if (bundletable==null)
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee_notable2,bundletable);
                else
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee,bundletable);
            }
        });
        trasua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundletable.putString("temp","trasua");
                if (bundletable==null) // đi từ home --> menu
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee_notable2,bundletable);
                else // đi từ table --> menu
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee,bundletable);
            }
        });
        sinhto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundletable.putString("temp","sinhto");
                if (bundletable==null)
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee_notable2,bundletable);
                else
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee,bundletable);
            }
        });
        topping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundletable.putString("temp","topping");
                if (bundletable==null)
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee_notable2,bundletable);
                else
                    Navigation.findNavController(view).navigate(R.id.action_menuMenu_to_fragment_menu_coffee,bundletable);
            }
        });
        return v;
    }
}