package com.example.coffee_cake;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    final Calendar myCalendar1 = Calendar.getInstance(),
            myCalendar2 = Calendar.getInstance();
    EditText edtDob, edtBegin;

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

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");

        edtDob =  (EditText)root.findViewById(R.id.edtDob);
        edtBegin = (EditText)root.findViewById(R.id.edtBeginDate);


        if (getArguments() != null) // Che do chinh sua nhan vien
        {
            Bundle data = getArguments();

            // hien thi thong tin co the thay doi duoc
            ((EditText)root.findViewById(R.id.edtName)).setText(data.getString("Fullname"));
            ((EditText)root.findViewById(R.id.edtCCCD)).setText(data.getString("CCCD"));
            ((EditText)root.findViewById(R.id.edtDob)).setText(data.getString("Dob"));
            ((EditText)root.findViewById(R.id.edtBeginDate)).setText(data.getString("BeginDate"));
            ((EditText)root.findViewById(R.id.edtPhone)).setText(data.getString("Phone"));
            ((EditText)root.findViewById(R.id.edtHSL)).setText(data.getFloat("HSL")+"");

            try {
                myCalendar1.setTime(dateFormat.parse(data.getString("Dob")));
                myCalendar2.setTime(dateFormat.parse(data.getString("BeginDate")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        edtDob.setOnClickListener(view -> {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    myCalendar1.set(Calendar.YEAR, year);
                    myCalendar1.set(Calendar.MONTH, month);
                    myCalendar1.set(Calendar.DAY_OF_MONTH, day);

                    ((EditText)view).setText(dateFormat.format(myCalendar1.getTime()));
                }
            };
            new DatePickerDialog(getActivity(), date, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
        });

        edtBegin.setOnClickListener(view -> {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    myCalendar2.set(Calendar.YEAR, year);
                    myCalendar2.set(Calendar.MONTH, month);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, day);

                    ((EditText)view).setText(dateFormat.format(myCalendar2.getTime()));
                }
            };
            new DatePickerDialog(getActivity(), date, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });

        ((Button)root.findViewById(R.id.btnSaveInfoStaff)).setOnClickListener(view ->{


            if (getArguments() != null) // edit mode
            {

            }
            else // add mode
            {

            }
        });

        return root;
    }

}