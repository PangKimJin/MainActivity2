package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExpenditureListCreateItem extends AppCompatActivity {
    Database db;
    EditText editItem, editPrice, editQuantity;
    Button btnAddItem;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_list_create_item);

        db = new Database(this);
        editItem = (EditText) findViewById(R.id.editText_name_createItemExpenditure);
        editQuantity = (EditText) findViewById(R.id.editText_quantity_createItemExpenditure);
        editPrice = (EditText) findViewById(R.id.editText_price_createItemExpenditure);
        btnAddItem = (Button) findViewById(R.id.button_Add_createItemExpenditure);
        btnBack =  findViewById(R.id.button_back_createItemExpenditure);

        final String expenditureListId = getIntent().getStringExtra("ListViewClickValue");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList(expenditureListId);
            }
        });
        addData(Integer.parseInt(expenditureListId));
    }

    public void backToShopList(String expenditureListId) {
        Intent intent = new Intent(this, ExpenditureListDisplay.class);

        String id = expenditureListId;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    public void addData(final int id) {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData4(editItem.getText().toString(),
                                Integer.parseInt(editQuantity.getText().toString()),
                                Double.parseDouble(editPrice.getText().toString()), id);
                        backToShopList("" + id);
                        if (isInserted) {
                            Toast.makeText(ExpenditureListCreateItem.this, "ITEM ADDED", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExpenditureListCreateItem.this, "FAILED TO ADD ITEM", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
