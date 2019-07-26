package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    Button checkOut;
    Button button_selectAll;
    TextView toolbar_shopping_list_display_title;
    TextView TextView_subTotal;
    boolean selected = false;
    ArrayList<Item> list;

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
        final ListView shopListView = findViewById(R.id.shopListView);
        list = populate(Integer.parseInt(shopListId));
        final ArrayList<Item> shopList = list;




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
        checkOut = findViewById(R.id.button_checkOut);
        button_selectAll = findViewById(R.id.button_selectAll);
        if (selected) {
            button_selectAll.setText("UNSELECT ALL");
        } else {
            button_selectAll.setText("SELECT ALL");
        }
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkOut(list, getListName(shopListId));
                unSelectAll(shopList);
            }
        });

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

        button_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected) {
                    unSelectAll(shopList);
                    selected = false;
                    button_selectAll.setText("SELECT ALL");
                } else {
                    selectAll(shopList);
                    selected = true;
                    button_selectAll.setText("UNSELECT ALL");
                }
                list = populate(Integer.parseInt(shopListId));
                final ArrayList<Item> shopList =  list;
                ItemListAdaptor adaptor = new ItemListAdaptor(shopping_list_display.this, R.layout.shoppinglist_adapter, shopList);
                shopListView.setAdapter(adaptor);
                shopListView.invalidateViews();
            }
        });
    }

    public boolean selectAll(ArrayList<Item> items) {
        for (Item item: items) {
            String ID =  Integer.toString(item.getId());
            String name = item.getName();
            String quantity = Integer.toString(item.getQuantity());
            String price = Double.toString(item.getPrice());
            String listId = Integer.toString(item.getListID());

            boolean isUpdated = db.updateData1(ID, name,
                    quantity, price, listId, "true");
            if(!isUpdated) {
                Toast.makeText(shopping_list_display.this, "Failed to select item", Toast.LENGTH_LONG).show();
            }

        }
        return true;
    }

    public boolean unSelectAll(ArrayList<Item> items) {
        for (Item item: items) {
            String ID =  Integer.toString(item.getId());
            String name = item.getName();
            String quantity = Integer.toString(item.getQuantity());
            String price = Double.toString(item.getPrice());
            String listId = Integer.toString(item.getListID());

            boolean isUpdated = db.updateData1(ID, name,
                    quantity, price, listId, "false");
            if(!isUpdated) {
                Toast.makeText(shopping_list_display.this, "Failed to unselect item", Toast.LENGTH_LONG).show();
            }

        }
        return true;
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
        ArrayList<Item> list = new ArrayList<>();
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
                    boolean selected = Boolean.parseBoolean(res.getString(5));
                    Log.d("myTag", Boolean.toString(selected));
                    Item item = new Item(id, name, quantity, price, listID, selected);
                    list.add(item);
                }

            }
            return list;
        }
    }

    public void checkOut(ArrayList<Item> items, String listName) {
        //Creating a default expenditureList
        db.insertData3("New Expenditure List", listName, getCurrentDate());

        //Creating item objects
        int ExpenditureListID = db.getLatestEntryIDExpenditureList();
        ArrayList<Item> expenditureItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isSelected()) {
                Item item = new Item(0, items.get(i).getName(), items.get(i).getQuantity(), items.get(i).getPrice(), ExpenditureListID);
                expenditureItems.add(item);
            }
        }

        //Inserting items into database
        for (Item item: expenditureItems) {
            String name = item.getName();
            int quantity = item.getQuantity();
            double price = item.getPrice();
            int ListID = item.getListID();
            db.insertData4(name, quantity, price, ListID);
        }
        goToEditExpenditureList(ExpenditureListID + "");
    }

    public void goToEditExpenditureList(String expenditureListID) {
        Intent intent = new Intent(this, EditExpenditureList.class);
        String id = expenditureListID;
        intent.putExtra("ExpenditureListID", id);
        startActivity(intent);
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / YYYY ");
        String strDate = "" + mdformat.format(calendar.getTime());
        return strDate;
    }
}
