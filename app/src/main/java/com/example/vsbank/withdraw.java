package com.example.vsbank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class withdraw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        Button withdraw_btn = findViewById(R.id.withdraw_btn);
        withdraw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText amount_e = findViewById(R.id.withdraw_amount);
                String amount = amount_e.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("withdraw_amount",amount);
                setResult(RESULT_OK, intent);
                Toast.makeText(withdraw.this, "Withdrawn Successfully!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}