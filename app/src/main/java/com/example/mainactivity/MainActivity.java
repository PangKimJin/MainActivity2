package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView buttonShopList;
    private CardView buttonScan;
    private CardView buttonExpenditure;
    private CardView buttonDashboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //CardViews as buttons to the activities
        buttonShopList = findViewById(R.id.my_shoplist);
        buttonExpenditure = findViewById(R.id.viewPastExpenditure);
        buttonScan = findViewById(R.id.ITTR);
        buttonDashboard = findViewById(R.id.dashboard);

        buttonShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserInput();
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageToTextRecognition();
            }
        });
        buttonExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPastExpenditure();
            }
        });
        buttonDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
    }

    public void openImageToTextRecognition() {
        Intent intent = new Intent(this, ITTR.class);
        startActivity(intent);
    }

    public void openUserInput() {
        Intent intent = new Intent(this, CreateLists.class);
        startActivity(intent);
    }

    public void openMyPastExpenditure() {
        Intent intent = new Intent(this, MyPastExpenditure.class);
        startActivity(intent);
    }

    public void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }


}
