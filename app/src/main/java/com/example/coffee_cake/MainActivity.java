package com.example.coffee_cake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        //Button btnInput = findViewById(R.id.btnInput);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent a = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(a);
            finish();
        }

        ((Button) findViewById(R.id.btnInput)).setOnClickListener(view ->
        {
            mAuth = FirebaseAuth.getInstance();

            String s1 = edtUsername.getText().toString(),
                    s2 = edtPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(s1, s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent a = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(a);
                        finish();
                    }
                }
            });

        });
    }
}
