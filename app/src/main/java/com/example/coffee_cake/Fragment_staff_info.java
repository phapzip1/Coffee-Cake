package com.example.coffee_cake;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;

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


        // get truc tiep tu firebase
        FirebaseFirestore.getInstance().collection("/NHANVIEN/").document(staffInfo.getString("MANV"))
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot snap =  task.getResult();
                    ((TextView)root.findViewById(R.id.edtName)).setText(snap.getString("HOTEN"));
                    ((TextView)root.findViewById(R.id.tvPosition)).setText(snap.getString("CHUCVU"));
                    ((TextView)root.findViewById(R.id.tvGender)).setText(snap.getString("GIOITINH"));
                    ((TextView)root.findViewById(R.id.tvCCCD)).setText(snap.getString("CCCD"));
                    ((TextView)root.findViewById(R.id.tvDob)).setText(snap.getString("NGAYSINH"));
                    ((TextView)root.findViewById(R.id.tvBeginDate)).setText(snap.getString("NGVL"));
                    ((TextView)root.findViewById(R.id.tvPhone1)).setText(snap.getString("SDT"));
                    ((TextView)root.findViewById(R.id.tvHSL)).setText( ""+snap.getDouble("HESOLUONG"));
                }
            }
        });

        ((ImageView)root.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("/NHANVIEN/").document(staffInfo.getString("MANV")).delete();
                FirebaseStorage.getInstance().getReference().child("images/staff/"+ staffInfo.getString("MANV" + ".jpg")).delete();
                Navigation.findNavController(view).navigate(R.id.action_fragment_staff_info_to_menuStaff);
            }
        });

        // cái này để test load ảnh xin đừng xóa


        ImageLoader.Load( "images/staff/" + staffInfo.getString("MANV") + ".jpg", ((ImageView)root.findViewById(R.id.avatar)));
        //hihi

        // get truc tiep tu data base





        ((ImageView)root.findViewById(R.id.btnEdit)).setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_staff_info_to_fragment_staff_edit, staffInfo);
        });

        return root;
    }
}