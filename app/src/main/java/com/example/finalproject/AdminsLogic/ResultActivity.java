package com.example.finalproject.AdminsLogic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapters.DistributionOfVotersByAgeAdapter;
import com.example.finalproject.Adapters.PartyAdapter;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.DBUtils.TemporaryFB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.AgesBlocks;
import com.example.finalproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private PieChart pieChart;
    private RoundedProgressBar pb_voters_prec;
    private RoundedProgressBar pb_by_sex_prec;
    private RecyclerView RV_DistributionByAge;
    private DistributionOfVotersByAgeAdapter adapter;
    private ArrayList agesBlocksList;
    private Spinner ages_dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();
        System.out.println(TemporaryDB.getAllVoters());
        pb_voters_prec.setProgressPercentage(32.6,true);
        pb_by_sex_prec.setProgressPercentage(32.6,true);
        showPieChart();
        createAgeBlocks();
        adapter = new DistributionOfVotersByAgeAdapter(this, agesBlocksList);
        RV_DistributionByAge.setLayoutManager(new LinearLayoutManager(this));
        RV_DistributionByAge.setAdapter(adapter);

        ages_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                createAgeBlocks();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void createAgeBlocks() {
        agesBlocksList = new ArrayList<AgesBlocks>();
        int oldesAge = TemporaryFB.getOldestAge();
        int startVotingAge = TemporaryFB.startVotingAge();
        int dropDownSelection = Integer.parseInt(ages_dropdown.getSelectedItem().toString());
        while (startVotingAge < oldesAge){
            agesBlocksList.add(new AgesBlocks(startVotingAge, startVotingAge + dropDownSelection,dropDownSelection));
            startVotingAge += dropDownSelection;
        }
        if(adapter != null){
            adapter.setAgesBlocksList(agesBlocksList);
            adapter.notifyDataSetChanged();
        }
    }

    private void findViews() {
        pb_voters_prec = findViewById(R.id.pb_voters_prec);
        pb_by_sex_prec = findViewById(R.id.pb_by_sex_prec);
        pieChart = findViewById(R.id.pieChart_view);
        RV_DistributionByAge = findViewById(R.id.RV_DistributionByAge);
        ages_dropdown = findViewById(R.id.ages_dropdown);
        refreshList();
    }


    private void refreshList() {
        List<String> ages = Arrays.asList("6", "8", "10", "12");

// Create an ArrayAdapter with the keysList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ages);

// Set the ArrayAdapter to the spinner
        ages_dropdown.setAdapter(adapter);
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