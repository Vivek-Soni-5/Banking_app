package com.example.vsbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class deposit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        Button deposit_btn = findViewById(R.id.deposit_btn);
        deposit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText amount_e = findViewById(R.id.deposit_amount);
                String amount = amount_e.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("deposit_amount",amount);
                setResult(RESULT_OK, intent);
                Toast.makeText(deposit.this, "Deposited Successfully!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}