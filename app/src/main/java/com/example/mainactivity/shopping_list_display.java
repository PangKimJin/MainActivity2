package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class shopping_list_display extends AppCompatActivity {
    Database db;
    Button btnAdd;
    TextView TextViewTitle;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_display);
        db = new Database(this);
        btnAdd = findViewById(R.id.button_To_User_Input);
        btnBack = findViewById(R.id.button_back);
        TextViewTitle = findViewById(R.id.textViewTitle);
        final String shopListId = getIntent().getStringExtra("ListViewClickValue");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInputForm(shopListId);
            }
        });
        ListView shopListView = findViewById(R.id.shopListView);
        final ArrayList<Item> shopList = populate(Integer.parseInt(shopListId));
        TextViewTitle.setText(getListName(shopListId));


        ItemListAdaptor adaptor = new ItemListAdaptor(this, R.layout.shoppinglist_adapter, shopList);
        shopListView.setAdapter(adaptor);
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tempListView =  shopList.get(i).getId() + "";

                Intent intent = new Intent(shopping_list_display.this, ItemDisplay.class);
                intent.putExtra("ListViewClickValue", tempListView);
                intent.putExtra("ShopListID", shopListId);
                startActivity(intent);
            }
        });
        back();

    }

    public void back() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList();

            }
        });
    }

    public void backToShopList() {
        Intent intent = new Intent(this, CreateLists.class);
        startActivity(intent);
    }
    public String getListName(String ID) {
        Cursor res = db.getAllData2();
        String name = "";
        while(res.moveToNext()) {
            int listID = Integer.parseInt(res.getString(0));
            if (listID == Integer.parseInt(ID)) {
                name = res.getString(1);
                break;
            }
        }
        return name;

    }
    public void openUserInputForm(String shopListID) {
        Intent intent = new Intent(this, UserInputForm.class);
        String id = shopListID;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    public ArrayList<Item> populate(int shopListID) {
        Cursor res = db.getAllData1();
        ArrayList list = new ArrayList();
        if (res.getCount() == 0) {
            //show empty list


            return list;
        } else {
            while (res.moveToNext()) {
                int listID = Integer.parseInt(res.getString(4));
                if (listID == shopListID) {
                    int id = Integer.parseInt(res.getString(0));
                    String name = res.getString(1);
                    int quantity = Integer.parseInt(res.getString(2));
                    double price = Double.parseDouble(res.getString(3));

                    Item item = new Item(id, name, quantity, price, listID);
                    list.add(item);
                }

            }
            return list;
        }
    }
}
