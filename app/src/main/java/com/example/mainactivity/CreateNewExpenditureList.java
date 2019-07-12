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

public class CreateNewExpenditureList extends AppCompatActivity {
    Database db;
    EditText editList;
    EditText editCategory;
    Button btnCreateList;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_expenditure_list);

        db = new Database(this);
        editList = (EditText) findViewById(R.id.editText_Name);
        btnCreateList = findViewById(R.id.button_create);
        editCategory = findViewById(R.id.editText_Category);
        btnBack =  findViewById(R.id.button_back);
        back();
        createList();
    }

    public void back() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMyPastExpenditure();
            }
        });
    }
    public void backToMyPastExpenditure() {
        Intent intent = new Intent(this, MyPastExpenditure.class);
        startActivity(intent);
    }

    public void createList() {
        btnCreateList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData3(editList.getText().toString(),
                                editCategory.getText().toString(),
                                getCurrentDate());
                        backToMyPastExpenditure();
                        if (isInserted) {
                            Toast.makeText(CreateNewExpenditureList.this, "LIST CREATED", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CreateNewExpenditureList.this, "FAILED TO CREATE LIST", Toast.LENGTH_LONG).show();
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
