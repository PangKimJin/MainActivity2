package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class Dashboard extends AppCompatActivity {
Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        db = new Database(this);
        //Pie Chart Stuff
        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new PieEntry(945f, "Groceries"));
        NoOfEmp.add(new PieEntry(1040f, "Recreational"));
        NoOfEmp.add(new PieEntry(1240f, "Transport"));
        NoOfEmp.add(new PieEntry(1133f, "Food"));
        NoOfEmp.add(new PieEntry(1252f, "Miscellaneous"));
//        NoOfEmp.add(new PieEntry(1345f, 5));
//        NoOfEmp.add(new PieEntry(1501f, 6));
//        NoOfEmp.add(new PieEntry(1545f, 7));
//        NoOfEmp.add(new PieEntry(1578f, 8));
//        NoOfEmp.add(new PieEntry(1945f, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Categories");
        PieData data;
        data = new PieData(dataSet);
        pieChart.setData(data);


        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setValueTextSize(10);
        dataSet.setValueTextColor(Color.rgb(35,35,35));
        pieChart.animateXY(1500, 1500);
//        pieChart.getDescription().setText("Expenditure Breakdown by Category");
        pieChart.getDescription().setPosition(0f,0f);
//        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Expenditure Breakdown by Category");
        pieChart.setHoleColor(Color.rgb(35,35,35));
        pieChart.setCenterTextSize(10);
        pieChart.setCenterTextColor(Color.rgb(255,255,255));
        pieChart.getLegend().setTextColor(Color.rgb(255,255,255));
        pieChart.setNoDataText("No data to display");
        pieChart.setNoDataTextColor(Color.rgb(255,255,255));
        pieChart.setEntryLabelTextSize(0);
        pieChart.getLegend().setTextSize(8);

        Legend pieLegend = pieChart.getLegend();
        pieLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        pieLegend.setDrawInside(false);


        //Bar Chart Stuff
        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> entries = getBarChartData();

        BarDataSet set = new BarDataSet(entries, "Expenditure");
        BarData barData = new BarData(set);
        barData.setBarWidth(0.9f);                                                                   // set custom bar width
        barChart.setData(barData);
        barChart.setFitBars(true);                                                                   // make the x-axis fit exactly all bars

//        set.setColor(Color.rgb(255,255,255));                                       //colours of the bars
        set.setColors(ColorTemplate.LIBERTY_COLORS);
        set.setValueTextSize(10);
        set.setValueTextColor(Color.rgb(35,35,35));
        barChart.getDescription().setText("Monthly Expenditure Breakdown");
        barChart.getDescription().setTextSize(10);
        barChart.getDescription().setPosition(750f,30.00f);
        barChart.getDescription().setTextColor(Color.rgb(255, 255, 255));           //colours of the description text
        barChart.getLegend().setTextColor(Color.rgb(255,255,255));                  //colour of the legend
        barChart.getXAxis().setDrawGridLines(false);                                                 //removed the x axis grid lines
        barChart.getXAxis().setTextColor(Color.rgb(255,255,255));                   //colour of the x axis (on top)
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisLeft().setTextColor(Color.rgb(255,255,255));                //colour of the y axis
        barChart.getAxisRight().setTextColor(Color.rgb(35,35,35));                  //colour of the right side y axis (same as background)
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getLegend().setEnabled(false);


        ArrayList<String> xAxisValues = new ArrayList<>(Arrays.asList("Zero", "Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"));
        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        barChart.setNoDataText("No data to display");
        barChart.setNoDataTextColor(Color.rgb(255,255,255));

        barChart.animateXY(0, 1500);

        barChart.invalidate();                                                                       // refresh

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

    //Returns arrayList for pie chart, according to category
    public ArrayList<PieEntry> getPieChartData() {
        //gets all expenditure lists
        ArrayList<ExpenditureList> expenditureLists = populate();
        HashMap<String, Double> categoryToExpenditureDict = new HashMap<>();

        //Add categories
        for (ExpenditureList list:expenditureLists) {
            String category = list.getCategory();
            if (!categoryToExpenditureDict.containsKey(category)) {
                categoryToExpenditureDict.put(category, 0.00);
            }
        }

        //Map category to expenditure
        for (ExpenditureList list: expenditureLists) {
            String category = list.getCategory();
            int listID = list.getId();
            double totalExpenditure = getTotal(populateItem(listID));
            double oldValue = categoryToExpenditureDict.get(category);
            categoryToExpenditureDict.put(category, oldValue + totalExpenditure);

        }
        ArrayList<PieEntry> result = new ArrayList<>();
        for (String cat : categoryToExpenditureDict.keySet()) {
            double expenditureDouble = categoryToExpenditureDict.get(cat);
            float expenditure = (float)expenditureDouble;
            PieEntry data = new PieEntry(expenditure, cat);
            result.add(data);
        }

        return result;

    }

    //Returns arrayList for bar chart, according to date
    public ArrayList<BarEntry> getBarChartData() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        int currentMonth = Integer.parseInt(simpleDateFormat.format(currentDate));
        ArrayList<ExpenditureList> ExpenditureLists = populate();
        HashMap<Integer, Double> monthToExpenditureDict = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            int key = (currentMonth - i + 12) % 12;

            monthToExpenditureDict.put(key, 0.00);
        }
        for (ExpenditureList list: ExpenditureLists) {
            String createdDate = list.getCreatedDate();
            int listID = list.getId();
            try {
                Log.d("myTag", createdDate);
                SimpleDateFormat formatter = new SimpleDateFormat("dd / MM / yyyy");
                Date expenditureDate = formatter.parse(createdDate);
//                Log.d("myTag", expenditureDate.toString());
                int expenditureMonth = Integer.parseInt(simpleDateFormat.format(expenditureDate));
//                Log.d("myTag", Integer.toString(expenditureMonth));
                if (monthToExpenditureDict.containsKey(expenditureMonth)) {
                    double oldValue = monthToExpenditureDict.get(expenditureMonth);
                    double totalExpenditure = getTotal(populateItem(listID));
//                    Log.d("myTag", Double.toString(totalExpenditure));
                    monthToExpenditureDict.put(expenditureMonth, oldValue + totalExpenditure);

                }
            } catch (Exception exception) {
                Toast.makeText(Dashboard.this, "Something went wrong with the dates", Toast.LENGTH_LONG).show();
            }

        }
        ArrayList<BarEntry> result = new ArrayList<>();
        for (int key: monthToExpenditureDict.keySet()) {
            double expenditureDouble = monthToExpenditureDict.get(key);
//            Log.d("myTag", Double.toString(expenditureDouble));
            float expenditure = (float)expenditureDouble;
            BarEntry data = new BarEntry(key, expenditure);
            result.add(data);
        }
        return result;


    }

    public String getMonthInString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        if (month == 0) {
            return "January";
        } else if (month == 1) {
            return "February";
        }else if (month == 2) {
            return "March";
        }else if (month == 3) {
            return "April";
        }else if (month == 4) {
            return "May";
        }else if (month == 5) {
            return "June";
        }else if (month == 6) {
            return "July";
        }else if (month == 7) {
            return "August";
        }else if (month == 8) {
            return "September";
        }else if (month == 9) {
            return "October";
        }else if (month == 10) {
            return "November";
        }else {
            return "December";
        }
    }

    public double getTotal(ArrayList<Item> list) {
        double total = 0.00;
        for (Item item: list) {
            double price = item.calculatePrice();
            total += price;
        }
        return total;
    }
    public ArrayList<Item> populateItem(int expenditureListId) {
        Cursor res = db.getAllData4();
        ArrayList<Item> list = new ArrayList<>();
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

                ExpenditureList expenditureList = new ExpenditureList(id, name, date, category);
                list.add(expenditureList);

            }
            return list;
        }
    }


}
