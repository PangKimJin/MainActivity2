package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        PieChart pieChart = findViewById(R.id.piechart);
//        ArrayList NoOfEmp = new ArrayList();
//
//        NoOfEmp.add(new PieEntry(945f, 0));
//        NoOfEmp.add(new PieEntry(1040f, 1));
//        NoOfEmp.add(new PieEntry(1240f, 2));
//        NoOfEmp.add(new PieEntry(1133f, 3));
//        NoOfEmp.add(new PieEntry(1252f, 4));
//        NoOfEmp.add(new PieEntry(1345f, 5));
//        NoOfEmp.add(new PieEntry(1501f, 6));
//        NoOfEmp.add(new PieEntry(1545f, 7));
//        NoOfEmp.add(new PieEntry(1578f, 8));
//        NoOfEmp.add(new PieEntry(1945f, 9));
//        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Money Spent");
//        PieData data;
//        data = new PieData(dataSet);
//        pieChart.setData(data);
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        pieChart.animateXY(1500, 1500);

        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 30));
        entries.add(new BarEntry(1, 80));
        entries.add(new BarEntry(2, 20));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData barData = new BarData(set);
        barData.setBarWidth(0.9f); // set custom bar width
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

//        ArrayList<String> xAxisLabels = new ArrayList<>();
//        xAxisLabels.add("January");
//        xAxisLabels.add("February");
//        xAxisLabels.add("March");
//        final ArrayList<String> xLabels = xAxisLabels;
//
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//
//                return xLabels.get((int) value);
//            }
//        });
    }
}
