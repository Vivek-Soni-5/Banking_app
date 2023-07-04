package com.example.vsbank;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class mainScreen extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher_deposit;
    private ActivityResultLauncher<Intent> activityResultLauncher_login;
    private ActivityResultLauncher<Intent> activityResultLauncher_withdraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //getting id's of account name,name and balance
        TextView acc_name = findViewById(R.id.acc_name);
        TextView acc_number = findViewById(R.id.account_number);
        TextView name = findViewById(R.id.name);
        TextView balance = findViewById(R.id.balance);
        TextView history1 = findViewById(R.id.history1);
        TextView history2 = findViewById(R.id.history2);
        TextView history3 = findViewById(R.id.history3);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        acc_name.setText(username);
        name.setText(username);

        myDatabaseHelper dbHelper = new myDatabaseHelper(this);
        int curr_balance = dbHelper.getBalance(username,password);
        int account_number = dbHelper.getAcc_num(username,password);
        balance.setText(String.valueOf(curr_balance));
        acc_number.setText(String.valueOf(account_number));


        // Initialize Activity Result Launchers
        activityResultLauncher_withdraw = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    float current_balance = Float.parseFloat(balance.getText().toString());
                    float withdraw_amount = Float.parseFloat(data.getStringExtra("withdraw_amount"));
                    float new_balance = current_balance-withdraw_amount;
                    int his = (int)-withdraw_amount;
                    balance.setText(Float.toString(new_balance));
                    dbHelper.setBalance(username,password, (int) new_balance);
                    dbHelper.insert_history(username,password,his);
                    String his1 = "5/12/2023(Withdrawn)                          ₹"+his+"\n\nRef id: 9287635                       balance: ₹"+new_balance;
                    history3.setText(history2.getText().toString());
                    history2.setText(history1.getText().toString());
                    history1.setText(his1);

                }
            }
        });
        // Initialize Activity Result Launchers
        activityResultLauncher_deposit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    float current_balance = Float.parseFloat(balance.getText().toString());
                    float deposit_amount = Float.parseFloat(data.getStringExtra("deposit_amount"));
                    float new_balance = current_balance+deposit_amount;
                    balance.setText(Float.toString(new_balance));
                    dbHelper.setBalance(username,password, (int) new_balance);
                    dbHelper.insert_history(username,password, (int) deposit_amount);
                    String his1 = "5/12/2023(deposited)                          ₹"+deposit_amount+"\n\nRef id: 1287635                       balance: ₹"+new_balance;
                    history3.setText(history2.getText().toString());
                    history2.setText(history1.getText().toString());
                    history1.setText(his1);
                }
            }
        });


        ImageView withdraw_img = findViewById(R.id.withdraw);
        ImageView deposit_img = findViewById(R.id.deposit);
        ImageView history_img = findViewById(R.id.history_img);
        TextView copy_logo = findViewById(R.id.copy_logo);

        copy_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mainScreen.this, "Copied to Clipboard!!!", Toast.LENGTH_SHORT).show();
            }
        });

        withdraw_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreen.this,withdraw.class);
                activityResultLauncher_withdraw.launch(intent);
            }

        });


        deposit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreen.this,deposit.class);
                activityResultLauncher_deposit.launch(intent);
            }
        });

        history_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreen.this,history.class);
//                activityResultLauncher_deposit.launch(intent);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
    }
}