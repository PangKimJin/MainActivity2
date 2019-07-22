package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenditureListDisplay extends AppCompatActivity {
    Database db;
    Button btnAdd;
    TextView TextViewTitle;
    TextView TextView_SubTotal;
    Button btnBack;
    Button btnDelete;

    Toolbar toolbar_expenditure_list_display;
    Button toolbar_expenditure_list_display_back;
    Button toolbar_expenditure_list_display_add;
    Button toolbar_expenditure_list_display_delete;
    TextView toolbar_expenditure_list_display_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_list_display);

        btnAdd = findViewById(R.id.button_CreateExpenditureList);
        btnBack = findViewById(R.id.button_backToMyExpenditures);
        TextViewTitle = findViewById(R.id.textViewTitleExpenditure);
        TextView_SubTotal = findViewById(R.id.textView_SubTotal);
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
        expenditureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tempListView =  expenditureListItems.get(i).getId() + "";

                Intent intent = new Intent(ExpenditureListDisplay.this, ExpenditureItemDisplay.class);
                intent.putExtra("ListViewClickValue", tempListView);
                intent.putExtra("ExpenditureListID", expenditureListId);
                startActivity(intent);
            }
        });


        back();

        //toolbar stuff
        toolbar_expenditure_list_display = findViewById(R.id.toolbar_shopping_list_display);
        toolbar_expenditure_list_display_back = findViewById(R.id.toolbar_shopping_list_display_back);
        toolbar_expenditure_list_display_add = findViewById(R.id.toolbar_shopping_list_display_add);
        toolbar_expenditure_list_display_delete = findViewById(R.id.toolbar_shopping_list_display_delete);
        toolbar_expenditure_list_display_title = findViewById(R.id.toolbar_shopping_list_display_title);

        toolbar_expenditure_list_display_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMyPastExpenditure();

            }
        });
        toolbar_expenditure_list_display_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpenditureListCreateItem(expenditureListId);
            }
        });
        DeleteItem(expenditureListId);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String total = "$" + df.format(getTotal(expenditureListItems));
        TextView_SubTotal.setText(total);
        toolbar_expenditure_list_display_title.setText(getListName(expenditureListId));
    }

    public double getTotal(ArrayList<Item> list) {
        double total = 0.00;
        for (Item item: list) {
            double price = item.calculatePrice();
            total += price;
        }
        return total;
    }

    public void DeleteItem(final String id) {
        toolbar_expenditure_list_display_delete.setOnClickListener(new View.OnClickListener() {
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
