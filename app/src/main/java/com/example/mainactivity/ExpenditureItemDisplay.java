package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExpenditureItemDisplay extends AppCompatActivity {
    Database db;
    EditText name, quantity, price;
    //    Button btnDelete;
//    Button btnEdit;
//    Button btnBack;
    Toolbar toolbar_item_display;
    Button toolbar_item_display_back;
    Button toolbar_item_display_delete;
    Button toolbar_item_display_tick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_item_display);
        db = new Database(this);
        final String itemId = getIntent().getStringExtra("ListViewClickValue");
        final String expenditureListID = getIntent().getStringExtra("ExpenditureListID");
        name = findViewById(R.id.editText_name_expenditureItem);
        quantity = findViewById(R.id.editText_quantity_expenditureItem);
        price = findViewById(R.id.editText_price_expenditureItem);
//        btnDelete = findViewById(R.id.button_delete);
//        btnBack = findViewById(R.id.button_back);
//        btnEdit = findViewById(R.id.button_edit);
        Item item = getItem(itemId);
        name.setText("" + item.getName());
        quantity.setText("" + item.getQuantity());
        price.setText("" + item.getPrice());


        //toolbar stuff
        toolbar_item_display = findViewById(R.id.toolbar_item_display);
        toolbar_item_display_back = findViewById(R.id.toolbar_item_display_back);
        toolbar_item_display_delete = findViewById(R.id.toolbar_item_display_delete);
        toolbar_item_display_tick = findViewById(R.id.toolbar_item_display_tick);

        DeleteItem(itemId, expenditureListID);
        back(expenditureListID);
        updateData(itemId, expenditureListID);
    }

    public void updateData(final String id, final String listID) {
        toolbar_item_display_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = db.updateExpenditureItem(id, name.getText().toString(),
                        quantity.getText().toString(), price.getText().toString(), listID);
                backToExpenditureListDisplay(listID);
                if(isUpdated == true) {
                    Toast.makeText(ExpenditureItemDisplay.this, "Item Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ExpenditureItemDisplay.this, "Item Update Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void back(final String shopListID) {
        toolbar_item_display_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToExpenditureListDisplay(shopListID);

            }
        });
    }

    public void backToExpenditureListDisplay(String shopListID) {
        Intent intent = new Intent(this, ExpenditureListDisplay.class);

        String id = shopListID;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    public void DeleteItem(final String id, final String listID) {
        toolbar_item_display_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRow = db.deleteExpenditureItem(id);
                backToExpenditureListDisplay(listID);
                if(deletedRow > 0) {
                    Toast.makeText(ExpenditureItemDisplay.this, "Item Successfully Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ExpenditureItemDisplay.this, "Item Deletion Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public Item getItem(String id) {
        Cursor res = db.getAllData4();
        Item result = new Item(0, "", 0, 0, 0);
        while (res.moveToNext()) {
            String itemID = res.getString(0);
            if (itemID.equals(id)) {
                int ID = Integer.parseInt(res.getString(0));
                String name = res.getString(1);
                int quantity = Integer.parseInt(res.getString(2));
                double price = Double.parseDouble(res.getString(3));

                Item item = new Item(ID, name, quantity, price, 0);
                result = item;
            }

        }
        return result;
    }
}
