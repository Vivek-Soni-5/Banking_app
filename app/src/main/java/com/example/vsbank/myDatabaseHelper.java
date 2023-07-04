package com.example.vsbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class myDatabaseHelper extends SQLiteOpenHelper {
    public myDatabaseHelper(Context context) {
        super(context, "database.db",null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE register(id INTEGER PRIMARY KEY, username TEXT,email TEXT,password TEXT,amount INTEGER default 1000.0,account_number INTEGER)";
        db.execSQL(createTableQuery);
        String createTableQuery2 = "CREATE TABLE history(id INTEGER PRIMARY KEY, username TEXT,password TEXT,history_amount INTEGER default 0)";
        db.execSQL(createTableQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS register");
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    public Boolean insert(String username,String email,String password)
    {
        //account number generator
        Random random = new Random();
        int randomNumber = 100000000 + random.nextInt();
        int acc_num = Math.abs(randomNumber);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email",email);
        values.put("password",password);
        values.put("account_number",acc_num);
        long result = db.insert("register", null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from register where username = ?",new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from register where email = ?",new String[]{email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkPassword(String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from register where password = ?",new String[]{password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

//    public Boolean amount(String username,String password,float balance)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from register where username = ? and password = ?",new String[]{username,password});
//        if(cursor.getCount()>0) {
//            int amount_d = (int)balance;
//            ContentValues values = new ContentValues();
//            values.put("amount", amount_d);
//            long newRowId = db.insert("register", null, values);
//            if (newRowId != -1) {
//                // Insert successful
//            } else {
//                // Insert failed
//            }
//            return true;
//        }
//        else
//            return false;
//    }

    public int getBalance(String username,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select amount from register where username =? and password = ?",new String[]{username,password});
        int amount = 1;
        if(cursor.moveToFirst())
        {
            do{
                amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
                Log.d("Amount", String.valueOf(amount));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return amount;
    }

    public int getAcc_num(String username,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select account_number from register where username =? and password = ?",new String[]{username,password});
        int acc_number = 1;
        if(cursor.moveToFirst())
        {
            do{
                acc_number = cursor.getInt(cursor.getColumnIndexOrThrow("account_number"));
                Log.d("account number", String.valueOf(acc_number));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return acc_number;
    }

    public void setBalance(String username,String password,int amount_balance)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update register set amount = ?  where username =? and password = ?",new Object[]{amount_balance,username,password});
        db.close();
    }

    public void insert_history(String username,String password,int amount_history)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into history(username,password,history_amount)values(?,?,?)",new Object[]{username,password,amount_history});
        db.close();
    }

    public ArrayList<String> getHistory(String username,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select history_amount from history where username =? and password = ?",new String[]{username,password});
        int amount = 1;
        ArrayList<String> arr = new ArrayList<>();
        String s;
        if(cursor.moveToFirst())
        {
            do{
                amount = cursor.getInt(cursor.getColumnIndexOrThrow("history_amount"));
//                Log.d("Amount", String.valueOf(amount));
                if(amount<=0)
                {
                    s = "5/12/2023(withdrawn)                          ₹"+amount+"\n\nRef id: 1287635                       ";
                    arr.add(s);
                }
                else
                {
                    s = "5/12/2023(deposited)                          ₹"+amount+"\n\nRef id: 1287635                       ";
                    arr.add(s);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arr;
    }

}
