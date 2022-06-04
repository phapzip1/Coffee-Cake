package com.example.coffee_cake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_SignUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText edtUser, edtPw, edtPwAgain, edtShopName;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtUser = (EditText) findViewById(R.id.edtUsername);
        edtPw = (EditText) findViewById(R.id.edtNewPw);
        edtPwAgain = (EditText) findViewById(R.id.edtPwAgain);
        edtShopName = (EditText) findViewById(R.id.edtShopName);

        ((Button) findViewById(R.id.btnInputSignUp)).setOnClickListener(view -> {
            RegisterUser();
        });


    }

    private void RegisterUser() {
        String userName = edtUser.getText().toString();
        String passWord = edtPw.getText().toString();
        String checkPass = edtPwAgain.getText().toString();
        String shopName = edtShopName.getText().toString();

        if(userName.isEmpty() || passWord.isEmpty()|| checkPass.isEmpty() || shopName.isEmpty()){
            Toast.makeText(getApplicationContext(), "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();
        }
        else{
            if(passWord.equals(checkPass)){
                mAuth.createUserWithEmailAndPassword(userName, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<String, Object> map = new HashMap<>();
                            map.put("TEN_CUAHANG", shopName);
                            map.put("NGAY_DK", Calendar.getInstance().getTime());

                            db.collection("CUAHANG").document(task.getResult().getUser().getUid()).set(map);

                            Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Đăng kí thất bại " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

}