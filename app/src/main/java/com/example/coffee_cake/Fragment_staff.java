package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_staff#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_staff extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_staff() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_staff.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_staff newInstance(String param1, String param2) {
        Fragment_staff fragment = new Fragment_staff();
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

    ArrayList<Staff> staffs;
    StaffAdapter adapter;
    ListView listView ;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_staff, container, false);

        DBHelper db = new DBHelper(getActivity());

        staffs = new ArrayList<>();
        adapter = new StaffAdapter(getActivity(), R.layout.layout_staff_manage, staffs);
        listView = (ListView) root.findViewById(R.id.ListOfStaffs);
        listView.setAdapter(adapter);
        staffs.clear();
        Cursor cs = db.getReadableDatabase().rawQuery("SELECT * FROM NHANVIEN", null);
        while(cs.moveToNext())
        {
            String CCCD = cs.getString(cs.getColumnIndex("CCCD")),
                    HOTEN = cs.getString(cs.getColumnIndex("HOTEN")),
                    NGAYSINH = cs.getString(cs.getColumnIndex("NGAYSINH")),
                    GIOITINH = cs.getString(cs.getColumnIndex("GIOITINH")),
                    SDT = cs.getString(cs.getColumnIndex("SDT")),
                    NGVL = cs.getString(cs.getColumnIndex("NGVL")),
                    CHUCVU = cs.getString(cs.getColumnIndex("CHUCVU")),
                    MANV = cs.getString(cs.getColumnIndex("MANV"));
            float HeSoLuong = cs.getFloat(cs.getColumnIndex("HESOLUONG"));
            staffs.add(new Staff(MANV, HOTEN, NGAYSINH, GIOITINH ,SDT, NGVL, CHUCVU, CCCD, HeSoLuong));
        }
        cs.close();
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putString("Fullname", staffs.get(i).HoVaTen());
            bundle.putString("Position", staffs.get(i).ChucVu());
            bundle.putString("Gender", staffs.get(i).GioiTinh());
            bundle.putString("CCCD", staffs.get(i).CCDD_CMND());
            bundle.putString("Dob", staffs.get(i).NgayThangNamSinh());
            bundle.putString("BeginDate", staffs.get(i).NgayVaoLam());
            bundle.putString("Phone", staffs.get(i).SoDienThoai());
            bundle.putFloat("HSL", staffs.get(i).HeSoLuong());
            bundle.putString("MANV", staffs.get(i).MaNhanVien());
            Navigation.findNavController(view).navigate(R.id.action_menuStaff_to_fragment_staff_info, bundle);
        });

        ((ImageView)root.findViewById(R.id.btnAddInfo)).setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_menuStaff_to_fragment_staff_edit);
        });

        return root;
    }
}