package com.example.coffee_cake;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_bill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_bill extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_bill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_bill.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_bill newInstance(String param1, String param2) {
        Fragment_bill fragment = new Fragment_bill();
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

    File path;
    ArrayList<Boolean> table;
    MyVM viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MyVM.class);
        table = viewModel.getTables();
        path = getContext().getFilesDir();

        getDateTime(view);

        getTableNumber(view);

        ((ImageView)view.findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_bill_to_menuDrinkTable);
            }
        });

        ((Button)view.findViewById(R.id.btnPay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayDialog(Gravity.CENTER, view);
            }
        });

        return view;
    }

    private void openPayDialog(int gravity, View view) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_payment);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else dialog.setCancelable(false);

        ((Button)dialog.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ((Button)dialog.findViewById(R.id.btnThanhToan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                changeTableStatus(bundle.getInt("key1"));
                Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_fragment_bill_to_menuDrinkTable);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeTableStatus(int tableNumber) {
        Bundle bundle = getArguments();
        String fileName = bundle.getString("key2");

        File savedFile = new File(path + "/" + fileName);
        if(!savedFile.exists()){
            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        }

        //thay đổi trạng thái
        table.set(tableNumber-1, false);
        viewModel.setTables(table);

        deleteFile(fileName);
        writeToFile(fileName);

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

    private void deleteFile(String fileName) {
        File savedFile = new File(path + "/" + fileName);

        if (!savedFile.exists())
            Toast.makeText(getContext(), "Không tồn tại file",
                    Toast.LENGTH_SHORT).show();
        else {
            savedFile.delete();
        }
    }

    private void getTableNumber(View view) {
        Bundle bundle = getArguments();
        ((TextView)view.findViewById(R.id.table_number)).setText("Bàn " + bundle.getInt("key1"));
    }

    private void getDateTime(View view) {
        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm EEEE, dd LLLL yyyy");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        ((TextView) view.findViewById(R.id.timeDate)).setText(dateTime);
    }
}