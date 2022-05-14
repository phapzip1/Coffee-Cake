package com.example.coffee_cake;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Product {
    private String masp,tensp;
    private int gia;

    public Product(String masp, String tensp, int gia) {
        this.masp = masp;
        this.tensp = tensp;
        this.gia = gia;
    }
    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}

class ProductAdapter extends BaseAdapter    // dùng cho phần xuất sản phẩm ra
{

    TextView tvname,tvmasp,tvprice;
    ImageView ava;
    private Context m_Context;
    private ArrayList<Product> m_array;
    private int m_Layout;
    public ProductAdapter(Context context, int layout, ArrayList<Product> arrayList)
    {
        m_Context = context;
        m_Layout = layout;
        m_array = arrayList;
    }
    @Override
    public int getCount() {
        return m_array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(m_Context).inflate(m_Layout,null);
        tvname = (TextView) view.findViewById(R.id.tvname);
        tvmasp = (TextView) view.findViewById(R.id.tvmasp);
        tvprice = (TextView) view.findViewById(R.id.tvprice);
        ava = (ImageView) view.findViewById(R.id.imageDrink);

        tvname.setText(m_array.get(i).getTensp());
        tvmasp.setText(m_array.get(i).getMasp());
        tvprice.setText(m_array.get(i).getGia()+"");
//        StorageReference pathReference = FirebaseStorage.getInstance("gs://firebasse-a6718.appspot.com").getReference().child("images/goods/CA01.jpg");
//        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(ava);
//            }
//        });
        // hue
        ImageLoader.Load("/images/goods/" + m_array.get(i).getMasp() + ".jpg", ava);
        Picasso.get().load("https://cdn.chotot.com/C_xyr_985fhfmnRunVDdkvpaW6asy_FbZUpKV7NfH38/preset:view/plain/eaadcc4a614a19526a3103b41f3db1d6-2751770130514705901.jpg").into(ava);

        return view;
    }

}
class ProductAdapterUpdate extends BaseAdapter // Dùng cho layout có thể update data
{
    TextView tvnameupdate, tvmaspupdate, tvpriceupdate;
    ImageView avaupdate;
    private Context m_Context;
    private ArrayList<Product> m_array;
    private int m_Layout;

    public ProductAdapterUpdate(Context context, int layout, ArrayList<Product> arrayList) {
        m_Context = context;
        m_Layout = layout;
        m_array = arrayList;
    }

    @Override
    public int getCount() {
        return m_array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { // chưa
        view = LayoutInflater.from(m_Context).inflate(m_Layout, null);
        tvnameupdate = (TextView) view.findViewById(R.id.tvnameupdate);
        tvmaspupdate = (TextView) view.findViewById(R.id.tvmaspupdate);
        tvpriceupdate = (TextView) view.findViewById(R.id.tvpriceupdate);
        avaupdate = (ImageView) view.findViewById(R.id.imageDrinkupdate);

        tvnameupdate.setText(m_array.get(i).getTensp());
        tvmaspupdate.setText(m_array.get(i).getMasp());
        tvpriceupdate.setText(m_array.get(i).getGia() + "");

        ImageLoader.Load( "/images/goods/"  + m_array.get(i).getMasp() + ".jpg", avaupdate);

        return view;
    }

}

