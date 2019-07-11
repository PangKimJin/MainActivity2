package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemDisplay extends AppCompatActivity {
    Database db;
    EditText name, quantity, price;
    Button btnDelete;
    Button btnEdit;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        final String itemId = getIntent().getStringExtra("ListViewClickValue");
        final String shopListID = getIntent().getStringExtra("ShopListID");
        name = findViewById(R.id.editTextName);
        quantity = findViewById(R.id.editTextQuantity);
        price = findViewById(R.id.editTextPrice);
        btnDelete = findViewById(R.id.button_delete);
        btnBack = findViewById(R.id.button_back);
        btnEdit = findViewById(R.id.button_edit);
        Item item = getItem(itemId);
        name.setText("" + item.getName());
        quantity.setText("" + item.getQuantity());
        price.setText("" + item.getPrice());
        DeleteItem(itemId, shopListID);
        back(shopListID);
        updateData(itemId, shopListID);

    }
    public void updateData(final String id, final String listID) {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = db.updateData1(id, name.getText().toString(),
                        quantity.getText().toString(), price.getText().toString(), listID);
                backToShopList(listID);
                if(isUpdated == true) {
                    Toast.makeText(ItemDisplay.this, "Item Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ItemDisplay.this, "Item Update Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void back(final String shopListID) {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList(shopListID);

            }
        });
    }

    public void backToShopList(String shopListID) {
        Intent intent = new Intent(this, shopping_list_display.class);

        String id = shopListID;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    public void DeleteItem(final String id, final String listID) {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRow = db.deleteItem(id);
                backToShopList(listID);
                if(deletedRow > 0) {
                    Toast.makeText(ItemDisplay.this, "Item Successfully Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ItemDisplay.this, "Item Deletion Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public Item getItem(String id) {
        Cursor res = db.getAllData1();
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


