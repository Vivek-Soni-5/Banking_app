package com.example.vsbank;

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
public class registration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText username1 = (EditText) findViewById(R.id.username);
        EditText password1 = findViewById(R.id.password);
        EditText email1 = findViewById(R.id.email);
        TextView signin = findViewById(R.id.signin);
        Button registerbtn = findViewById(R.id.registration_btn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registration.this,login.class);
                startActivity(intent);
            }
        });
        //Access the Database
        myDatabaseHelper dbHelper = new myDatabaseHelper(this);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username1.getText().toString();
                String password = password1.getText().toString();
                String email = email1.getText().toString();
                if(username.equals("") || password.equals("") || email.equals("")) {
                    Toast.makeText(registration.this, "All fields are mandatory to fill!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean insert_b = dbHelper.insert(username,email,password);
                    if(insert_b == true) {
                        Toast.makeText(registration.this, "Registration Successfull!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registration.this,login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(registration.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}