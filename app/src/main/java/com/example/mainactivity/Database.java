package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Shop_List.db";
    public static final String TABLE_NAME = "Shopping_List";
    public static final String TABLE_LISTS = "Lists";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Item_Name";
    public static final String COL_3 = "Quantity";
    public static final String COL_4 = "Expected_Price";
    public static final String COL_5 = "List_ID";
    public static final String ID = "ID";
    public static final String NAME = "List_Name";
    public static final String CREATED_DATE  = "Created_Date";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Item_Name TEXT, Quantity INTEGER, Expected_Price DOUBLE, List_ID INTEGER)");
        db.execSQL("Create Table " + TABLE_LISTS +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "List_Name TEXT, Created_Date DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData1(String item, int quantity, double price, int listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, quantity);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, listID);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getAllData1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean insertData2(String item, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, item);
        contentValues.put(CREATED_DATE, date);

        long result = db.insert(TABLE_LISTS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_LISTS, null);
        return result;
    }

}
