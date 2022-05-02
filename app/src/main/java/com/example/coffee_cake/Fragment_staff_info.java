package com.example.coffee_cake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_staff_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_staff_info extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_staff_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_staff_info.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_staff_info newInstance(String param1, String param2) {
        Fragment_staff_info fragment = new Fragment_staff_info();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_staff_info, container, false);

        Bundle staffInfo = getArguments();

        ((TextView)root.findViewById(R.id.edtName)).setText(staffInfo.getString("Fullname"));
        ((TextView)root.findViewById(R.id.tvPosition)).setText(staffInfo.getString("Position"));
        ((TextView)root.findViewById(R.id.tvGender)).setText(staffInfo.getString("Gender"));
        ((TextView)root.findViewById(R.id.tvCCCD)).setText(staffInfo.getString("CCCD"));
        ((TextView)root.findViewById(R.id.tvDob)).setText(staffInfo.getString("Dob"));
        ((TextView)root.findViewById(R.id.tvBeginDate)).setText(staffInfo.getString("BeginDate"));
        ((TextView)root.findViewById(R.id.tvPhone1)).setText(staffInfo.getString("Phone"));
        ((TextView)root.findViewById(R.id.tvHSL)).setText( ""+staffInfo.getFloat("HSL"));

        ((ImageView)root.findViewById(R.id.btnEdit)).setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_staff_info_to_fragment_staff_edit, staffInfo);
        });

        return root;
    }
}