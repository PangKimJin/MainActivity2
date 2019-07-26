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

public class ItemDisplay extends AppCompatActivity {
    Database db;
    EditText name, quantity, price;
//    Button btnDelete;
//    Button btnEdit;
//    Button btnBack;
    Toolbar toolbar_item_display;
    Button toolbar_item_display_back;
    Button toolbar_item_display_delete;
    Button toolbar_item_display_tick;
    Button selected;
    boolean isSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //non-toolbar stuff
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        final String itemId = getIntent().getStringExtra("ListViewClickValue");
        final String shopListID = getIntent().getStringExtra("ShopListID");
        name = findViewById(R.id.editTextName);
        quantity = findViewById(R.id.editTextQuantity);
        price = findViewById(R.id.editTextPrice);
        selected = findViewById(R.id.button_GotIt);
//        btnDelete = findViewById(R.id.button_delete);
//        btnBack = findViewById(R.id.button_back);
//        btnEdit = findViewById(R.id.button_edit);
        Item item = getItem(itemId);
        name.setText("" + item.getName());
        quantity.setText("" + item.getQuantity());
        price.setText(String.format("%.2f", item.getPrice()));
        isSelected = item.isSelected();


        //toolbar stuff
        toolbar_item_display = findViewById(R.id.toolbar_item_display);
        toolbar_item_display_back = findViewById(R.id.toolbar_item_display_back);
        toolbar_item_display_delete = findViewById(R.id.toolbar_item_display_delete);
        toolbar_item_display_tick = findViewById(R.id.toolbar_item_display_tick);

        DeleteItem(itemId, shopListID);
        back(shopListID);
        updateData(itemId, shopListID);
        gotIt(itemId, shopListID);

        if(isSelected) {
            selected.setText("UNSELECT");
            selected.setBackgroundResource(R.drawable.redbuttonshape);

        } else {
            selected.setText("GOT IT!");
            selected.setBackgroundResource(R.drawable.greenbuttonshape);

        }
    }
    public void gotIt(final String itemId, final String shopListID) {
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
                    boolean isUpdated = db.updateData1(itemId, name.getText().toString(),
                            quantity.getText().toString(), price.getText().toString(), shopListID, "false");
                    isSelected = false;
                    selected.setText("GOT IT!");
                    backToShopList(shopListID);
                    if (isUpdated) {
                        Toast.makeText(ItemDisplay.this, "Item Unselected", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ItemDisplay.this, "Unable to unselect Item", Toast.LENGTH_LONG).show();
                    }
                } else {
                    boolean isUpdated = db.updateData1(itemId, name.getText().toString(),
                            quantity.getText().toString(), price.getText().toString(), shopListID, "true");
                    selected.setText("UNSELECT");
                    backToShopList(shopListID);
                    if (isUpdated) {
                        Toast.makeText(ItemDisplay.this, "Item Bought", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ItemDisplay.this, "Unable to select Item", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void updateData(final String id, final String listID) {
        toolbar_item_display_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = db.updateData1(id, name.getText().toString(),
                        quantity.getText().toString(), price.getText().toString(), listID, "false");
                backToShopList(listID);
                if(isUpdated) {
                    Toast.makeText(ItemDisplay.this, "Item Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ItemDisplay.this, "Item Update Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void back(final String shopListID) {
        toolbar_item_display_back.setOnClickListener(new View.OnClickListener() {
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
        toolbar_item_display_delete.setOnClickListener(new View.OnClickListener() {
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
                boolean selected = Boolean.parseBoolean(res.getString(5));

                Item item = new Item(ID, name, quantity, price, 0, selected);
                result = item;
            }

        }
        return result;
    }


}


