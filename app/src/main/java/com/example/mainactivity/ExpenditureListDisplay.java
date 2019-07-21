package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExpenditureListDisplay extends AppCompatActivity {
    Database db;
    Button btnAdd;
    TextView TextViewTitle;
    Button btnBack;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_list_display);

        btnAdd = findViewById(R.id.button_CreateExpenditureList);
        btnBack = findViewById(R.id.button_backToMyExpenditures);
        TextViewTitle = findViewById(R.id.textViewTitleExpenditure);
        btnDelete = findViewById(R.id.button_deleteExpenditureList);

        final String expenditureListId = getIntent().getStringExtra("ListViewClickValue");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpenditureListCreateItem(expenditureListId);
            }
        });
        ListView expenditureListView = findViewById(R.id.ExpenditureListView);
        final ArrayList<Item> expenditureListItems = populate(Integer.parseInt(expenditureListId));
        TextViewTitle.setText(getListName(expenditureListId));


        ExpenditureListDisplayAdaptor adaptor = new ExpenditureListDisplayAdaptor(this, R.layout.expenditure_list_display_adaptor, expenditureListItems);
        expenditureListView.setAdapter(adaptor);
        DeleteItem(expenditureListId);


    }

    public void DeleteItem(final String id) {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRow = db.deleteExpenditureList(id);
                backToMyPastExpenditure();
                if(deletedRow > 0) {
                    Toast.makeText(ExpenditureListDisplay.this, "List Successfully Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ExpenditureListDisplay.this, "List Deletion Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
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
    public String getListName(String ID) {
        Cursor res = db.getAllData3();
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
    public void openExpenditureListCreateItem(String shopListID) {
        Intent intent = new Intent(this, ExpenditureListCreateItem.class);
        String id = shopListID;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    public ArrayList<Item> populate(int expenditureListId) {
        Cursor res = db.getAllData4();
        ArrayList<Item> list = new ArrayList();
        if (res.getCount() == 0) {
            //show empty list


            return list;
        } else {
            while (res.moveToNext()) {
                int listID = Integer.parseInt(res.getString(4));
                if (listID == expenditureListId) {
                    int id = Integer.parseInt(res.getString(0));
                    String name = res.getString(1);
                    int quantity = Integer.parseInt(res.getString(2));
                    double price = Double.parseDouble(res.getString(3));

                    Item item = new Item(id, name, quantity, price, listID);
                    list.add(item);
                }

            }
            return list;
        }
    }
}
