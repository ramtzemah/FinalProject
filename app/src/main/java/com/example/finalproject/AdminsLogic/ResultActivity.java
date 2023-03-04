package com.example.finalproject.AdminsLogic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private PieChart pieChart;
    private RoundedProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();
        System.out.println(TemporaryDB.getAllVoters());
        progressBar.setProgressPercentage(32.6,true);
        showPieChart();
    }

    private void findViews() {
        progressBar = findViewById(R.id.progressBar);
        pieChart = findViewById(R.id.pieChart_view);
    }

    private void showPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "מפלגות:";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("עוצמה יהודית",200);
        typeAmountMap.put("לפיד",230);
        typeAmountMap.put("כחול לבן",100);
        typeAmountMap.put("ישראל ביתנו",50);
        typeAmountMap.put("ליכוד",500);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}