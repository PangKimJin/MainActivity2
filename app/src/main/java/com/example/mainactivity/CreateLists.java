package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateLists extends AppCompatActivity {
    Database db;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lists);
        db = new Database(this);
        ListView shopListView = findViewById(R.id.Lists);
        btn = findViewById(R.id.button_create);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNewList();
            }
        });

        ArrayList<List> Lists = populate();

        ShopListAdaptor adaptor = new ShopListAdaptor(this, R.layout.create_lists_adaptor, Lists);
        shopListView.setAdapter(adaptor);

    }
    public void openCreateNewList() {
        Intent intent = new Intent(this, Create_New_List.class);
        startActivity(intent);
    }
    public ArrayList<List> populate() {
        Cursor res = db.getAllData2();
        ArrayList list = new ArrayList();
        if (res.getCount() == 0) {
            //show empty list

            List empty = new List(0, "", "");
            list.add(empty);
            return list;
        } else {
            while (res.moveToNext()) {
                int id = Integer.parseInt(res.getString(0));
                String name = res.getString(1);

                String date =  (res.getString(2));

                List shoppingList = new List(id, name, date);
                list.add(shoppingList);

            }
            return list;
        }
    }
}


