package com.example.mainactivity;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPastExpenditure extends AppCompatActivity {
    Database db;
    Button buttonBack;
    Button buttonAdd;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_past_expenditure);

        db = new Database(this);
        ListView expenditureListView = findViewById(R.id.expenditureList);
        buttonAdd = findViewById(R.id.button_add);
        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomePage();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNewExpenditureList();
            }
        });

        final ArrayList<ExpenditureList> Lists = populate();

        MyPastExpenditureAdaptor adaptor = new MyPastExpenditureAdaptor(MyPastExpenditure.this, R.layout.my_past_expenditure_adaptor, Lists);
        expenditureListView.setAdapter(adaptor);
        expenditureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tempListView =  Lists.get(i).getId() + "";
                Intent intent = new Intent(MyPastExpenditure.this, ExpenditureListDisplay.class);
                intent.putExtra("ListViewClickValue", tempListView);
                startActivity(intent);
            }
        });

        //Toolbar Stuff
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Expenditure List");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mypastexpenditure,menu);  //inflates the menu_mypastexpenditure to the toolbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {       //what happens when the icons are clicked
        int id = item.getItemId();
        if (id == R.id.back_toolbar) {                          //back icon is clicked
            backToHomePage();
        } else if (id == R.id.add_toolbar) {                    //add icon is clicked
            openCreateNewExpenditureList();
        }
        return super.onOptionsItemSelected(item);
    }

    public void backToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openCreateNewExpenditureList() {
        Intent intent = new Intent(this, CreateNewExpenditureList.class);
        startActivity(intent);
    }
    public ArrayList<ExpenditureList> populate() {
        Cursor res = db.getAllData3();
        ArrayList<ExpenditureList> list = new ArrayList<>();
        if (res.getCount() == 0) {
            //show empty list



            return list;
        } else {
            while (res.moveToNext()) {
                int id = Integer.parseInt(res.getString(0));
                String name = res.getString(1);
                String category =  (res.getString(2));
                String date =  (res.getString(3));

                ExpenditureList expenditureList = new ExpenditureList(id, name, category, date);
                list.add(expenditureList);

            }
            return list;
        }
    }
}
