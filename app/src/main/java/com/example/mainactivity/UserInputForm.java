package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInputForm extends AppCompatActivity {
    Database db;
    EditText editItem, editPrice, editQuantity;
    Button btnAddItem;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_form);
        db = new Database(this);
        editItem = (EditText) findViewById(R.id.editText_Item);
        editQuantity = (EditText) findViewById(R.id.editText_Quantity);
        editPrice = (EditText) findViewById(R.id.editText_Price);
        btnAddItem = (Button) findViewById(R.id.button_add);
        btnBack =  findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList();
            }
        });
        addData();
    }
    public void backToShopList() {
        Intent intent = new Intent(this, shopping_list_display.class);
        startActivity(intent);
    }

    public void addData() {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData(editItem.getText().toString(),
                                Integer.parseInt(editQuantity.getText().toString()),
                                Double.parseDouble(editPrice.getText().toString()));
                        if (isInserted) {
                            Toast.makeText(UserInputForm.this, "ITEM ADDED", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserInputForm.this, "FAILED TO ADD ITEM", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
