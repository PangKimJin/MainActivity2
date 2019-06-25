package com.example.mainactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Shop_Items.db";
    public static final String TABLE_NAME = "Shopping_List";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Item_Name";
    public static final String COL_3 = "Expected_Price";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Item_Name TEXT, Expected_Price INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
