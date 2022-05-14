package com.example.coffee_cake;

import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ImageLoader {
    public static void Load(String URL, ImageView view)
    {
        StorageReference path = FirebaseStorage.getInstance().getReference().child(URL);
        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(view);
            }
        });
    }
}
