package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;

import java.util.Date;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Shop_List.db";
    public static final String TABLE_NAME = "Shopping_List";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Item_Name";
    public static final String COL_3 = "Quantity";
    public static final String COL_4 = "Expected_Price";
    public static final String COL_5 = "List_ID";
    public static final String COL_6 = "Selected";

    public static final String TABLE_LISTS = "Lists";
    public static final String ID = "ID";
    public static final String NAME = "List_Name";
    public static final String CREATED_DATE  = "Created_Date";

    public static final String TABLE_EXPENDITURE = "Expenditure";
    public static final String NUMBER = "id";
    public static final String EXPENDITURE = "List_Name";
    public static final String CATEGORY = "Category";
    public static final String DATE  = "Created_Date";

    public static final String TABLE_ITEMS = "ExpenditureItems";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_NAME = "ItemName";
    public static final String ITEM_QUANTITY = "Item_Quantity";
    public static final String ITEM_PRICE = "Price";
    public static final String LIST_ID = "Expenditure_List_ID";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Item_Name TEXT, Quantity INTEGER, Expected_Price DOUBLE, List_ID INTEGER, Selected BOOLEAN)");
        db.execSQL("Create Table " + TABLE_LISTS +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "List_Name TEXT, Created_Date DOUBLE)");
        db.execSQL("Create Table " + TABLE_EXPENDITURE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "List_Name TEXT, Category TEXT, Created_Date DOUBLE)");
        db.execSQL("Create Table "
                + TABLE_ITEMS +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ItemName TEXT, Item_Quantity INTEGER, Price DOUBLE, Expenditure_List_ID INTEGER)");


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
        contentValues.put(COL_6, false);
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

    public boolean updateData1(String ID, String name, String quantity, String price, String listID, String selected) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, quantity);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, listID);
        contentValues.put(COL_6, selected);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {ID});
        return true;
    }

    public Integer deleteList(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LISTS, "ID = ?", new String[] {id});

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
    public Integer deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});

    }

    public boolean insertData3(String item, String category, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENDITURE, item);
        contentValues.put(CATEGORY, category);
        contentValues.put(DATE, date);

        long result = db.insert(TABLE_EXPENDITURE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int getLatestEntryIDExpenditureList() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EXPENDITURE, null);
        cursor.moveToLast();
        int ID = Integer.parseInt(cursor.getString(0));
        return ID;
    }


    public Cursor getAllData3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_EXPENDITURE, null);
        return result;
    }

    public Integer deleteExpenditureList(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENDITURE, "ID = ?", new String[] {id});

    }

    public boolean updateExpenditureList(String ID, String name, String category, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER, ID);
        contentValues.put(EXPENDITURE, name);
        contentValues.put(CATEGORY, category);
        contentValues.put(DATE, date);

        db.update(TABLE_EXPENDITURE, contentValues, "ID = ?", new String[] {ID});
        return true;
    }

    public boolean insertData4(String name, int quantity, double price, int listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, name);
        contentValues.put(ITEM_QUANTITY, quantity);
        contentValues.put(ITEM_PRICE, price);
        contentValues.put(LIST_ID, listID);


        long result = db.insert(TABLE_ITEMS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getAllData4() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_ITEMS, null);
        return result;
    }

    public Integer deleteExpenditureItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, "ID = ?", new String[] {id});

    }

    public boolean updateExpenditureItem(String ID, String name, String quantity, String price, String listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ID, ID);
        contentValues.put(ITEM_NAME, name);
        contentValues.put(ITEM_QUANTITY, quantity);
        contentValues.put(ITEM_PRICE, price);
        contentValues.put(LIST_ID, listID);
        db.update(TABLE_ITEMS, contentValues, "ID = ?", new String[] {ID});
        return true;
    }
}
