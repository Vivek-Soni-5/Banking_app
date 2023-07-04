package com.example.vsbank;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register = findViewById(R.id.register_link);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,registration.class);
                startActivity(intent);
            }
        });

        myDatabaseHelper dbHelper = new myDatabaseHelper(this);

        EditText username1 = findViewById(R.id.username);
        EditText password1 = findViewById(R.id.password);

        Button login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = username1.getText().toString();
                String password = password1.getText().toString();
                Boolean user_b = dbHelper.checkUsername(username);
                Boolean pass_b = dbHelper.checkPassword(password);

                if(username.equals("") || password.equals(""))
                {
                    Toast.makeText(login.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(user_b == true)
                    {
                        if(pass_b == true)
                        {
                            Toast.makeText(login.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this,mainScreen.class);
                            intent.putExtra("username",username);
                            intent.putExtra("password",password);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(login.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(login.this, "User not Exist! Register Now!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}