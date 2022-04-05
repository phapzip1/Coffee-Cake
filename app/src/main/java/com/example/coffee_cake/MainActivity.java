package com.example.coffee_cake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        User user1 = new User("admin", "123");
        User user2 = new User("staff1", "123");
        User user3 = new User("staff2", "123");
        user = new ArrayList<>();
        user.add(user1);
        user.add(user2);
        user.add(user3);

        EditText edtUsername = findViewById(R.id.inputUsername);
        EditText edtPassword = findViewById(R.id.inputPassword);
        Button btnInput = findViewById(R.id.btnInput);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNAME = edtUsername.getText().toString();
                String passWORD = edtPassword.getText().toString();
                int i;
                boolean isCheck = false;
                for(i=0;i < user.size();i++) {
                    if (user.get(i).getUserName().compareTo(userNAME) == 0 && user.get(i).getPassWord().compareTo(passWORD)==0) {
                        isCheck = true;
                        break;
                    }

                }
                if (isCheck) {
                    Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("username", user.get(i).getUserName());
                    intent.putExtra("password", user.get(i).getPassWord());
                    startActivity(intent);
                }
                else
                {
                    showToast("Login is failed");
                }

            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}