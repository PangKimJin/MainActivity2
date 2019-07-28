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

public class EditExpenditureListLongHold extends AppCompatActivity {
    Database db;
    EditText name, category, date;
    //    Button btnDelete;
//    Button btnEdit;
//    Button btnBack;
    Toolbar toolbar_edit_expenditure_list;
    Button toolbar_edit_expenditure_list_back;
    Button toolbar_edit_expenditure_list_tick;
    Button button_editExpenditureList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenditure_list_long_hold);

        db = new Database(this);

        final String expenditureListID = getIntent().getStringExtra("ExpenditureListID");
        name = findViewById(R.id.editText_name_expenditureListLongHold);
        category = findViewById(R.id.editText_category_expenditureListLongHold);
        date = findViewById(R.id.editText_date_expenditureListLongHold);
        button_editExpenditureList = findViewById(R.id.button_editExpenditureListLongHold);
//        btnDelete = findViewById(R.id.button_delete);
//        btnBack = findViewById(R.id.button_back);
//        btnEdit = findViewById(R.id.button_edit);
        ExpenditureList list = getList(expenditureListID);
        name.setText("" + list.getName());
        category.setText("" + list.getCategory());
        date.setText("" + list.getCreatedDate());


        //toolbar stuff
        toolbar_edit_expenditure_list = findViewById(R.id.toolbar_edit_expenditure_list);
        toolbar_edit_expenditure_list_back = findViewById(R.id.toolbar_edit_expenditure_list_back);


        toolbar_edit_expenditure_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToExpenditureList();

            }
        });

//        DeleteItem(itemId, expenditureListID);
//        back(expenditureListID);
        updateData(expenditureListID);
    }

    public void updateData(final String id) {
        button_editExpenditureList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = db.updateExpenditureList(id, name.getText().toString(),
                        category.getText().toString(), date.getText().toString());
                backToExpenditureListDisplay(id);
                if(isUpdated == true) {
                    Toast.makeText(EditExpenditureListLongHold.this, "List Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditExpenditureListLongHold.this, "List Update Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

//    public void back(final String shopListID) {
//        toolbar_item_display_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                backToExpenditureListDisplay(shopListID);
//
//            }
//        });
//    }

    public void backToExpenditureList() {
        Intent intent = new Intent(this, MyPastExpenditure.class);
        startActivity(intent);
    }

    public void backToExpenditureListDisplay(String shopListID) {
        Intent intent = new Intent(this, ExpenditureListDisplay.class);

        String id = shopListID;
        intent.putExtra("ListViewClickValue", id);
        startActivity(intent);
    }

    //    public void DeleteItem(final String id, final String listID) {
//        toolbar_item_display_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int deletedRow = db.deleteExpenditureItem(id);
//                backToExpenditureListDisplay(listID);
//                if(deletedRow > 0) {
//                    Toast.makeText(ExpenditureItemDisplay.this, "Item Successfully Deleted", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(ExpenditureItemDisplay.this, "Item Deletion Unsuccessful", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }
    public ExpenditureList getList(String id) {
        Cursor res = db.getAllData3();
        ExpenditureList result = new ExpenditureList(0, "", "", "");
        while (res.moveToNext()) {
            String ExpenditureListID = res.getString(0);
            if (ExpenditureListID.equals(id)) {
                int ID = Integer.parseInt(res.getString(0));
                String name = res.getString(1);
                String category = res.getString(2);
                String date = res.getString(3);

                ExpenditureList list = new ExpenditureList(ID, name, date, category);
                result = list;
            }

        }
        return result;
    }
}
