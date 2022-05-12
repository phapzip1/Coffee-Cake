package com.example.coffee_cake;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_order extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_order.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_order newInstance(String param1, String param2) {
        Fragment_order fragment = new Fragment_order();
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
    TextView name,soluong,gia,s,m,l,tvQuantity,tvPrice;
    Button btnthemngay;
    Boolean bs,bl,bm;
    ImageView add,remove;
    int sl,soban;
    Bundle bund;
    String size = "S";
    MaterialCardView selectCard;
    TextView tvtopping;
    boolean[] selecttopping;
    ArrayList<Integer> toppinglist; // Integer?
    //String[] toppingAraay = {"Trân châu","Khoai lang","Bánh Plan"}; // cái này đổi nữa
    File path;
    ArrayList<Boolean> table;
    MyVM viewModel;
    Cursor cursor = null;
    ArrayList<Product> arraytopping;

    String[] mangtengiatopping,mangtentopping;
    int[] manggiatopping;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MyVM.class);
        table = viewModel.getTables();
        path = getContext().getFilesDir();
        arraytopping = new ArrayList<>();
        // ------------------------ Phần sử lý topping
        selectCard = v.findViewById(R.id.selectCard);
        tvtopping = (TextView) v.findViewById(R.id.tvtopping);
        toppinglist = new ArrayList<>(); // lưu vị trí của từng phần tử


        DBHelper db = new DBHelper(getActivity());
        cursor = db.getReadableDatabase().rawQuery("SELECT * FROM SANPHAM WHERE MASP LIKE 'TO%' ",null);
        int i=0;

        while(cursor.moveToNext())
        {
            String MASP = cursor.getString(cursor.getColumnIndex("MASP"));

            String TENSP = cursor.getString(cursor.getColumnIndex("TENSP"));


            int GIA = cursor.getInt(cursor.getColumnIndex("GIA"));


            Product temp = new Product(MASP,TENSP,GIA);
            arraytopping.add(temp); //  --> số lượng topping
        }
//        mangtentopping[i] = TENSP;
//        manggiatopping[i] = cursor.getInt(cursor.getColumnIndex("GIA"));

        mangtentopping = new String[arraytopping.size()];
        manggiatopping = new int[arraytopping.size()];
        mangtengiatopping = new String[arraytopping.size()];

        for (int z=0;z<arraytopping.size();z++)
        {
            manggiatopping[z] = arraytopping.get(z).getGia();
            mangtentopping[z] = arraytopping.get(z).getTensp();
            mangtengiatopping[z] = arraytopping.get(z).getTensp() + "\t \t \t" + manggiatopping[z];
        }
        selecttopping = new boolean[mangtengiatopping.length]; // lưu đã chọn phần tử đó chưa (T/F)

        selectCard.setOnClickListener(new View.OnClickListener() { // bật dialog
            @Override
            public void onClick(View view) {
                showtoppingDialog();
            }
            private void showtoppingDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // khởi tạo hộp thoại

                builder.setTitle("Lựa Chọn Topping");
                builder.setCancelable(false); // đặt hộp thoại không thể hủy

                // String[] toppingAraay = {"Trân châu","Khoai lang","Bánh Plan"};
                // boolean[] selecttopping;
                // ArrayList<Integer> toppinglist;

                builder.setMultiChoiceItems(mangtengiatopping, selecttopping, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) // khi click vào thì sẽ được thêm vào list ( T/F )
                            //Khi lựa chọn trong chatbox --> thêm vào trong list
                            toppinglist.add(i);
                        else
                            //Khi không chọn trong chatbox --> xóa vào trong list
                            toppinglist.remove(Integer.valueOf(i));
                    }
                }).setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        StringBuilder stringBuilder = new StringBuilder();

                        for ( int j=0; j < toppinglist.size();j++) // toppinglist(int): thứ tự các món đã chọn
                        {
                            // stringBuilder: 1 cái mảng lấy thành phần trong box
                            stringBuilder.append(mangtengiatopping[toppinglist.get(j)]);
                            //String giatemp = gia.toString();
                            //gia.setText(giatemp + manggiatopping[j]);
                            if (j != toppinglist.size() -1 )
                            {
                                // để kiểm tra giá trị j và thêm vào dấu ","
                                stringBuilder.append(", ");
                            }
                            tvtopping.setText(stringBuilder.toString());
                        }

                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNeutralButton("Xóa tất cả", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for ( int j = 0 ; j < selecttopping.length ; j++) // đã có cái nào chọn rồi thì false nó lại
                        {
                            selecttopping[j] = false;
                            toppinglist.clear();
                            tvtopping.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
    // --------------------------------------------------------------
        name = (TextView) v.findViewById(R.id.tvOrder);
        soluong = (TextView) v.findViewById(R.id.tvQuantity);

        gia = (TextView) v.findViewById(R.id.tvPrice);

        s = (TextView) v.findViewById(R.id.sizeS);
        m = (TextView) v.findViewById(R.id.sizeM);
        l = (TextView) v.findViewById(R.id.sizeL);

        bund = getArguments(); // lấy giá trị, có số bàn

        soban = bund.getInt("soban");
        add = (ImageView) v.findViewById(R.id.btnAdd);
        remove = (ImageView) v.findViewById(R.id.btnRemove);
        sl = 1;
        bs = true;
        bm = false;
        bl = false;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl++;
                soluong.setText(String.valueOf(sl));
                if (bs)
                {
                    gia.setText(String.valueOf(sl*bund.getInt("GIA")));
                }
                else if (bm)
                {
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 5000 ) ));
                }
                else if (bl)
                {
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 10000 ) ));
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sl>1)
                {
                    sl--;
                    soluong.setText(String.valueOf(sl));

                    gia.setText(String.valueOf(sl*bund.getInt("GIA")));

                }
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bm==true || bl== true )
                {
                    size = "S";
                    gia.setText(String.valueOf(sl*bund.getInt("GIA")));
                    bs = true;
                    s.setTextColor(Color.parseColor("#ffffff"));
                    s.setBackgroundResource(R.drawable.round_bg);

                    bm = bl = false;
                    m.setText("M");
                    m.setTextColor(Color.parseColor("#111111"));
                    m.setBackgroundResource(R.drawable.round_bg_white);

                    l.setText("L");
                    l.setTextColor(Color.parseColor("#000000"));
                    l.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bs==true || bl== true )
                {
                    size = "M";
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 5000 ) ));
                    bm = true;
                    m.setTextColor(Color.parseColor("#ffffff"));
                    m.setBackgroundResource(R.drawable.round_bg);


                    bs = bl = false;
                    s.setText("S");
                    s.setTextColor(Color.parseColor("#111111"));
                    s.setBackgroundResource(R.drawable.round_bg_white);

                    l.setText("L");
                    l.setTextColor(Color.parseColor("#000000"));
                    l.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( bm==true || bs== true )
                {
                    size = "L";
                    gia.setText(String.valueOf(sl*( bund.getInt("GIA") + 10000 ) ));
                    bl = true;
                    l.setTextColor(Color.parseColor("#ffffff"));
                    l.setBackgroundResource(R.drawable.round_bg);

                    bm = bs = false;
                    m.setText("M");
                    m.setTextColor(Color.parseColor("#111111"));
                    m.setBackgroundResource(R.drawable.round_bg_white);

                    s.setText("S");
                    s.setTextColor(Color.parseColor("#000000"));
                    s.setBackgroundResource(R.drawable.round_bg_white);

                }
            }
        });
        name.setText(bund.getString("TENSP"));
        gia.setText(String.valueOf(bund.getInt("GIA")));

        btnthemngay = (Button) v.findViewById(R.id.btnOrderNow);
        btnthemngay.setOnClickListener(new View.OnClickListener() { // tên(size), số lượng ,topping, số bàn
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // đưa giá trị đến home
                bundle.putString("name",name.getText().toString());
                bundle.putString("size",size.toString());
                bundle.putString("soluong",soluong.getText().toString());
                bundle.putInt("soban",soban);
                //bundle.putString("gia",gia.getText().toString());
                changeTableStatus(soban);
                Navigation.findNavController(view).navigate(R.id.action_fragment_order_to_menuHome,bundle);
            }
        });
        return v;
    }

    private void changeTableStatus(int soban) {
        Bundle bundle = getArguments();
        String fileName = bundle.getString("fileName");

        File savedFile = new File(path + "/" + fileName);
        if(!savedFile.exists()){
            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        }

        //thay đổi trạng thái
        table.set(soban, true);
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
}