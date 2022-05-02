package com.example.coffee_cake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_staff_edit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_staff_edit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_staff_edit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_staff_edit.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_staff_edit newInstance(String param1, String param2) {
        Fragment_staff_edit fragment = new Fragment_staff_edit();
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

    ArrayAdapter<String> genderAdaper;
    ArrayList<String> gender;

    ArrayAdapter<String> positionAdapter;
    ArrayList<String> positions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_staff_edit, container, false);

        //set spinner
        gender = new ArrayList<>();
        genderAdaper = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, gender);
        ((Spinner)root.findViewById(R.id.editGender)).setAdapter(genderAdaper);
        gender.add("Nam");
        gender.add("Nữ");
        gender.add("Khác");

        if (getArguments() != null) // Che do chinh sua nhan vien
        {
            Bundle data = getArguments();

            // hien thi thong tin co the thay doi duoc
            ((EditText)root.findViewById(R.id.edtName)).setText(data.getString("Fullname"));
            ((EditText)root.findViewById(R.id.edtCCCD)).setText(data.getString("CCCD"));
            ((EditText)root.findViewById(R.id.edtDob)).setText(data.getString("Dob"));
            ((EditText)root.findViewById(R.id.edtBeginDate)).setText(data.getString("BeginDate"));
            ((EditText)root.findViewById(R.id.edtPhone)).setText(data.getString("Phone"));
            ((EditText)root.findViewById(R.id.edtHSL)).setText(data.getString("HSL"));
        }




        return root;
    }

}