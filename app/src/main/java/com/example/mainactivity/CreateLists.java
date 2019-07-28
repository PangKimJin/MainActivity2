package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CreateLists extends AppCompatActivity {
    Database db;
//    Button btn;                                                                                   //redundant since the buttons are now in the toolbar
//    Button btnBack;
    Toolbar toolbar_create_lists;
    Button toolbar_create_lists_back;
    Button toolbar_create_lists_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lists);
        db = new Database(this);
        ListView shopListView = findViewById(R.id.Lists);
//        btn = findViewById(R.id.button_create);                                                   //redundant since the buttons are now in the toolbar
//        btnBack = findViewById(R.id.button_back);
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                backToHomePage();
//            }
//        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openCreateNewList();
//            }
//        });

        final ArrayList<List> Lists = populate();

        ShopListAdaptor adaptor = new ShopListAdaptor(this, R.layout.create_lists_adaptor, Lists);
        shopListView.setAdapter(adaptor);
        shopListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tempListView =  Lists.get(i).getId() + "";
                Intent intent = new Intent(CreateLists.this, EditShopList.class);
                intent.putExtra("ShopListID", tempListView);
                startActivity(intent);
                return true;
            }
        });
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tempListView =  Lists.get(i).getId() + "";
                Intent intent = new Intent(CreateLists.this, shopping_list_display.class);
                intent.putExtra("ListViewClickValue", tempListView);
                startActivity(intent);
            }
        });

        //toolbar stuff
        toolbar_create_lists = findViewById(R.id.toolbar_create_lists);
        toolbar_create_lists_back = findViewById(R.id.toolbar_create_lists_back);
        toolbar_create_lists_add = findViewById(R.id.toolbar_create_lists_add);
        toolbar_create_lists_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomePage();
            }
        });
        toolbar_create_lists_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNewList();
            }
        });
    }


    public void backToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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


