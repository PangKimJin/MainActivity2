package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Create_New_List extends AppCompatActivity {
    Database db;
    EditText editList;
    Button btnCreateList;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__list);

        db = new Database(this);
        editList = (EditText) findViewById(R.id.editText_Name);
        btnCreateList = findViewById(R.id.button_create);
        btnBack =  findViewById(R.id.btn_back);
        back();
        createList();

    }

    public void back() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToShopList();
            }
        });
    }
    public void backToShopList() {
        Intent intent = new Intent(this, CreateLists.class);
        startActivity(intent);
    }

    public void createList() {
        btnCreateList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData2(editList.getText().toString(),
                                getCurrentDate());
                        if (isInserted) {
                            Toast.makeText(Create_New_List.this, "LIST CREATED", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Create_New_List.this, "FAILED TO CREATE LIST", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / YYYY ");
        String strDate = "" + mdformat.format(calendar.getTime());
        return strDate;
    }
}


