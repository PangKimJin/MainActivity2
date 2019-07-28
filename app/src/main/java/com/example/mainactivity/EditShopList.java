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

public class EditShopList extends AppCompatActivity {
    Database db;
    EditText name, category, date;
    //    Button btnDelete;
//    Button btnEdit;
//    Button btnBack;
    Toolbar toolbar_edit_expenditure_list;
    Button toolbar_edit_expenditure_list_back;
    Button toolbar_edit_expenditure_list_tick;
    Button button_editShopList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_list);

        db = new Database(this);

        final String shopListID = getIntent().getStringExtra("ShopListID");
        name = findViewById(R.id.editText_name_editShopList);

        date = findViewById(R.id.editText_date_editShopList);
        button_editShopList = findViewById(R.id.button_editShopList);
//        btnDelete = findViewById(R.id.button_delete);
//        btnBack = findViewById(R.id.button_back);
//        btnEdit = findViewById(R.id.button_edit);
        List list = getList(shopListID);
        name.setText("" + list.getName());
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
        updateData(shopListID);
    }

    public void updateData(final String id) {
        button_editShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = db.updateShopList(id, name.getText().toString(), date.getText().toString());
                backToShopListDisplay(id);
                if(isUpdated == true) {
                    Toast.makeText(EditShopList.this, "List Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditShopList.this, "List Update Unsuccessful", Toast.LENGTH_LONG).show();
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

    public void backToShopListDisplay(String shopListID) {
        Intent intent = new Intent(this, shopping_list_display.class);

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
    public List getList(String id) {
        Cursor res = db.getAllData2();
        List result = new List(0, "", "");
        while (res.moveToNext()) {
            String ShopListID = res.getString(0);
            if (ShopListID.equals(id)) {
                int ID = Integer.parseInt(res.getString(0));
                String name = res.getString(1);

                String date = res.getString(2);

                List list = new List(ID, name, date);
                result = list;
            }

        }
        return result;
    }
}
