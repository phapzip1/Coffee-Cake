package com.example.coffee_cake;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_drinks_edit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_drinks_edit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_drinks_edit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_drinks_edit.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_drinks_edit newInstance(String param1, String param2) {
        Fragment_drinks_edit fragment = new Fragment_drinks_edit();
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

    EditText edtNameDrink, edtPrice;
    ImageView imgDrink;
    Button btnSaveDrink;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Uri data = result.getData().getData();
                        imgDrink.setImageURI(data);
                    }
                }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_drinks_edit, container, false);

        edtNameDrink = (EditText)root.findViewById(R.id.edtNameDrink);
        edtPrice = (EditText)root.findViewById(R.id.edtPrice);
        imgDrink = (ImageView) root.findViewById(R.id.imgEditDink);
        btnSaveDrink = (Button) root.findViewById(R.id.btnSaveDrink);

        // thay đổi ảnh drink
        imgDrink.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            launcher.launch(galleryIntent);
        });

        btnSaveDrink.setOnClickListener(view -> {
            // đợi hoàn thành firestore database
            ImageLoader.Upload("images/goods/", imgDrink);
            // push du lieu len firestore database
        });

        Bundle DrinkEdit = getArguments();


        // ổn
        if (getArguments() != null) // Che do chinh sua drink
        {
            Bundle data = getArguments();

            // hien thi thong tin co the thay doi duoc
            FirebaseFirestore.getInstance().collection("/SANPHAM/CAPHE/DANHSACHCAPHE/").document(data.getString("MASP"))
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snap = task.getResult();
                        edtNameDrink.setText(snap.getString("TEN"));
                        edtPrice.setText( ""+snap.getLong("GIA"));
                    }
                }
            });
            ImageLoader.Load("images/goods/" + data.getString("MASP") + ".jpg", ((ImageView)root.findViewById(R.id.imgEditDink)));

        }



        ((Button)root.findViewById(R.id.btnSaveDrink)).setOnClickListener(view ->{

            String tensp = edtNameDrink.getText().toString(),
                    gia = edtPrice.getText().toString();

            if (tensp == ""  || gia == "")
            {
                Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            long giasp = Integer.parseInt(gia);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if (getArguments() == null) // add mode
            {


                Map<String, Object> drink = new HashMap<>();
                drink.put("TEN", tensp);
                drink.put("GIA", giasp);


                db.collection("/SANPHAM/CAPHE/DANHSACHCAPHE/").add(drink).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            ImageLoader.Upload("images/goods/" + task.getResult().getId() + ".jpg", imgDrink);
                        }
                    }
                });

            }
            else // edit mode
            {

                Map<String, Object> drink = new HashMap<>();
                drink.put("TEN", tensp);
                drink.put("GIA", giasp);

                String id = getArguments().getString("MASP");

                db.collection("/SANPHAM/CAPHE/DANHSACHCAPHE/").document(id).set(drink);

                ImageLoader.Upload("images/goods/" + id + ".jpg", imgDrink);
            }
            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();


//            Navigation.findNavController(view).navigate(R.id.action_fragment_drinks_edit_to_fragment_menu_coffee_notable2);
        });


        return root;
    }
}