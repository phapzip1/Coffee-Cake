package com.example.coffee_cake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        EditText edtUsername = findViewById(R.id.inputUsername);
        EditText edtPassword = findViewById(R.id.inputPassword);
        ImageView hide = findViewById(R.id.hidepw);
        ImageView show = findViewById(R.id.showpw);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                show.setVisibility(view.INVISIBLE);
                hide.setVisibility(view.VISIBLE);
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide.setVisibility(view.INVISIBLE);
                show.setVisibility(view.VISIBLE);
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent a = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(a);
            finish();
        }

        ((Button) findViewById(R.id.btnInput)).setOnClickListener(view -> {
            mAuth = FirebaseAuth.getInstance();

            String s1 = edtUsername.getText().toString(),
                    s2 = edtPassword.getText().toString();

            if (!s1.isEmpty() && !s2.isEmpty()) {
                mAuth.signInWithEmailAndPassword(s1, s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent a = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(a);
                            CToast.i(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG);
                            finish();

                        }
                        else {
                            CToast.e(MainActivity.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_LONG);
                        }
                    }
                });
            }
            else
                CToast.e(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG);



        });
        ((TextView) findViewById(R.id.btnSignUp)).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity_SignUp.class);
            startActivity(intent);

        });

        ((TextView) findViewById(R.id.btnSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity_SignUp.class));
            }
        });
    }
}
