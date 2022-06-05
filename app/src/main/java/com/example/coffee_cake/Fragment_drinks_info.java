package com.example.coffee_cake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_drinks_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_drinks_info extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_drinks_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_drinks_info.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_drinks_info newInstance(String param1, String param2) {
        Fragment_drinks_info fragment = new Fragment_drinks_info();
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


    String query = "";
    FirebaseAuth mAuth;
    DocumentReference db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_drinks_info, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance().document("CUAHANG/" + mAuth.getUid());

        Bundle DrinkInfo = getArguments();

        switch (DrinkInfo.getString("temp"))
        {
            case "coffee":
                query = "/SANPHAM/CAPHE/DANHSACHCAPHE";
                break;
            case "trasua":
                query = "/SANPHAM/TRASUA/DANHSACHTRASUA";
                break;
            case "sinhto":
                query = "/SANPHAM/SINHTO/DANHSACHSINHTO";
                break;
            case "topping":
                query = "/SANPHAM/TRANGMIENG/DANHSACHTRANGMIENG";
                break;
        }

        db.collection(query).document(DrinkInfo.getString("Masp"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot snap =  task.getResult();

                    ((TextView)root.findViewById(R.id.tvNameDrinks)).setText(snap.getString("TEN"));
                    ((TextView)root.findViewById(R.id.tvGia)).setText(""+snap.getLong("GIA")+" đ");


                }
            }
        });

        ((ImageView)root.findViewById(R.id.btnDelDrink)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(query).document(DrinkInfo.getString("Masp")).delete();
                FirebaseStorage.getInstance().getReference().child("images/goods/"+ DrinkInfo.getString("Masp" + ".jpg")).delete();
                CToast.i(getContext(), "Xóa thành công", Toast.LENGTH_SHORT);

                getActivity().onBackPressed();
            }
        });


        ImageLoader.Load( "images/goods/" + DrinkInfo.getString("Masp") + ".jpg", ((ImageView)root.findViewById(R.id.avtDrink)));


        ((ImageView)root.findViewById(R.id.btnEditDrink)).setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_drinks_info_to_fragment_drinks_edit, DrinkInfo);
        });

        return root;
    }
}