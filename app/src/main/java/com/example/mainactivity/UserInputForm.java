package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInputForm extends AppCompatActivity {
    Database db;
    EditText editItem, editPrice;
    Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_form);

        editItem = (EditText) findViewById(R.id.editText_Item);
        editPrice = (EditText) findViewById(R.id.editText_Price);
        btnAddItem = (Button) findViewById(R.id.button_add);
        addData();
    }

    public void addData() {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData(editItem.getText().toString(),
                                Integer.parseInt(editPrice.getText().toString()));
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
