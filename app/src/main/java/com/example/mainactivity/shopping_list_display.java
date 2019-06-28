package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class shopping_list_display extends AppCompatActivity {
    Database db;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_display);
        db = new Database(this);
        btn = findViewById(R.id.button_To_User_Input);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInputForm();
            }
        });
        ListView shopListView = findViewById(R.id.shopListView);
        ArrayList shopList = populate();
        ItemListAdaptor adaptor = new ItemListAdaptor(this, R.layout.shoppinglist_adapter, shopList);
        shopListView.setAdapter(adaptor);

    }
    public void openUserInputForm() {
        Intent intent = new Intent(this, UserInputForm.class);
        startActivity(intent);
    }

    public ArrayList<Item> populate() {
        Cursor res = db.getAllData();
        ArrayList list = new ArrayList();
        if (res.getCount() == 0) {
            //show empty list

            list.add(new Item(0,"", 0, 0) );
            return list;
        } else {
            while (res.moveToNext()) {
                int id = Integer.parseInt(res.getString(0));
                String name = res.getString(1);
                int quantity = Integer.parseInt(res.getString(2));
                double price = Double.parseDouble(res.getString(3));
                Item item = new Item(id, name, quantity, price);
                list.add(item);

            }
            return list;
        }
    }
}
