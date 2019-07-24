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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class shopping_list_display extends AppCompatActivity {
    Database db;
//    Button btnAdd;
    TextView TextViewTitle;
//    Button btnBack;
//    Button btnDelete;
    Toolbar toolbar_shopping_list_display;
    Button toolbar_shopping_list_display_back;
    Button toolbar_shopping_list_display_add;
    Button toolbar_shopping_list_display_delete;
    TextView toolbar_shopping_list_display_title;
    TextView TextView_subTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_display);
        db = new Database(this);
//        btnAdd = findViewById(R.id.button_To_User_Input);
//        btnBack = findViewById(R.id.button_back);
        TextViewTitle = findViewById(R.id.textViewTitle);
//        btnDelete = findViewById(R.id.button_delete);

        final String shopListId = getIntent().getStringExtra("ListViewClickValue");
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openUserInputForm(shopListId);
//            }
//        });
        ListView shopListView = findViewById(R.id.shopListView);
        final ArrayList<Item> shopList = populate(Integer.parseInt(shopListId));



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
//        back();


        //toolbar stuff
        toolbar_shopping_list_display = findViewById(R.id.toolbar_shopping_list_display);
        toolbar_shopping_list_display_back = findViewById(R.id.toolbar_shopping_list_display_back);
        toolbar_shopping_list_display_add = findViewById(R.id.toolbar_shopping_list_display_add);
        toolbar_shopping_list_display_delete = findViewById(R.id.toolbar_shopping_list_display_delete);
        toolbar_shopping_list_display_title = findViewById(R.id.toolbar_shopping_list_display_title);
        TextView_subTotal = findViewById(R.id.textView_total);

        toolbar_shopping_list_display_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList();

            }
        });
        toolbar_shopping_list_display_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInputForm(shopListId);
            }
        });
        DeleteItem(shopListId);

        toolbar_shopping_list_display_title.setText(getListName(shopListId));
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String total = "$ " + String.format("%.2f", getTotal(shopList));
        TextView_subTotal.setText(total);
    }

    public void DeleteItem(final String id) {
        toolbar_shopping_list_display_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRow = db.deleteList(id);
                backToShopList();
                if(deletedRow > 0) {
                    Toast.makeText(shopping_list_display.this, "List Successfully Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(shopping_list_display.this, "List Deletion Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

//    public void back() {
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                backToShopList();
//
//            }
//        });
//    }

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

    public double getTotal(ArrayList<Item> list) {
        double total = 0.00;
        for (Item item: list) {
            double price = item.calculatePrice();
            total += price;
        }
        return total;
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
