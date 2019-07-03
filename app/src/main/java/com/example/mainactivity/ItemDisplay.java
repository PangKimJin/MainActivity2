package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        final String itemId = getIntent().getStringExtra("ListViewClickValue");
        name = findViewById(R.id.editTextName);
        quantity = findViewById(R.id.editTextQuantity);
        price = findViewById(R.id.editTextPrice);
        btnDelete = findViewById(R.id.button_delete);
        Item item = getItem(itemId);
        name.setText("" + item.getName());
        quantity.setText("" + item.getQuantity());
        price.setText("" + item.getPrice());
        DeleteItem(itemId);


    }
    public void DeleteItem(final String id) {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRow = db.deleteItem(id);
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
            String itemID = res.getString(4);
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


