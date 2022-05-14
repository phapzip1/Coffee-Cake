package com.example.coffee_cake;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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
    EditText edtDob, edtBegin, edtName, edtId, edtIdentityNum,edtPhone, edtHSL;
    ImageView avatar;
    Button btnSave;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                         if (result.getResultCode() == Activity.RESULT_OK)
                         {
                             Uri data = result.getData().getData();
                             avatar.setImageURI(data);
                         }
                }
            });

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

        // initialize widget
        edtDob =  (EditText)root.findViewById(R.id.edtDob);
        edtBegin = (EditText)root.findViewById(R.id.edtBeginDate);
        edtName = (EditText)root.findViewById(R.id.edtName);
        edtIdentityNum = (EditText)root.findViewById(R.id.edtCCCD);
        edtPhone = (EditText)root.findViewById(R.id.edtPhone);
        edtHSL =  (EditText)root.findViewById(R.id.edtHSL);
        avatar = (ImageView) root.findViewById(R.id.avatar);
        btnSave = (Button) root.findViewById(R.id.btnSaveInfoStaff);


        avatar.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            launcher.launch(galleryIntent);
        });

        btnSave.setOnClickListener(view -> {

            // đợi hoàn thành firestore database
            ImageLoader.Upload("images/staff/", avatar);
            // push du lieu len firestore database
        });

        if (getArguments() != null) // Che do chinh sua nhan vien
        {
            Bundle data = getArguments();

            // hien thi thong tin co the thay doi duoc
            edtName.setText(data.getString("Fullname"));
            edtId.setText(data.getString("CCCD"));
            edtDob.setText(data.getString("Dob"));
            edtBegin.setText(data.getString("BeginDate"));
            edtPhone.setText(data.getString("Phone"));
            edtHSL.setText(data.getFloat("HSL")+"");

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