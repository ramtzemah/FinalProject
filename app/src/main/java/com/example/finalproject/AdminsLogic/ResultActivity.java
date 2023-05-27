package com.example.finalproject.AdminsLogic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapters.DistributionOfVotersByAgeAdapter;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.AgesBlocks;
import com.example.finalproject.Enums.Gender;
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
    private TextView title;
    private PieChart pieChart;
    private RoundedProgressBar pb_voters_prec;
    private RoundedProgressBar pb_by_sex_prec;
    private RecyclerView RV_DistributionByAge;
    private DistributionOfVotersByAgeAdapter adapter;
    private ArrayList agesBlocksList;
    private Spinner ages_dropdown;
    private String area;
    private DbUtils dbUtils;
    private Map<Integer, Integer> allVotersByAgeBlock;
    private Map<Integer, Integer> allVotersHowVoteByAgeBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();

        dbUtils = new DbUtils();
        area = getIntent().getStringExtra("area");
        if (area.equals("all")) {
            getDataAllCountry();
            title.setText("All Country");
            showPieChartAllCountry();
        } else {
            title.setText(area);
            getDataByArea();
            showPieChartByArea();
        }


        ages_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                createAgeBlocks();
                if (area.equals("all")) {
                    agesBlocksData();
                } else {
                    title.setText(area);
                    agesBlocksDataByArea();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void getDataAllCountry() {
        dbUtils.getAllVotesInCountry(Constant.DataBaseName, Constant.VotesCollectionNew, (result, t) -> {
            if (result != null) {
                dbUtils.getAllVotesAndSex(Constant.DataBaseName, Constant.VotesCollectionNew, Gender.זכר.toString(), (success, error) -> {
                    if (success != null) {
                        double boysGirlsVote = calculatePercentage((long) success, (long) result);
                        pb_by_sex_prec.setProgressPercentage(boysGirlsVote, true);
                    }
                });
                dbUtils.getAllVotersAllCountry(Constant.DataBaseName, Constant.VotersCollection, (success, error) -> {
                    if (success != null) {
                        double votNotVote = calculatePercentage((long) result, (long) success);
                        pb_voters_prec.setProgressPercentage(votNotVote, true);
                    }
                });
            }
        });
    }


    private void agesBlocksDataByArea() {
        int dropDownSelection = Integer.parseInt(ages_dropdown.getSelectedItem().toString());
        dbUtils.getAllAgeVoterByArea(Constant.DataBaseName, Constant.VotersCollection, area, dropDownSelection, (result, t) -> {
            if (result != null) {
                Map<Integer, Integer> temp = (Map<Integer, Integer>) result;
                for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                    allVotersByAgeBlock.put(entry.getKey(),
                            entry.getValue());
                }
                dbUtils.getAllAgeVoterHowVoteByArea(Constant.DataBaseName, Constant.VotersCollection, area, dropDownSelection, (success, t2) -> {
                    if (success != null) {
                        Map<Integer, Integer> temp2 = (Map<Integer, Integer>) success;
                        for (Map.Entry<Integer, Integer> entry2 : temp2.entrySet()) {
                            allVotersHowVoteByAgeBlock.put(entry2.getKey(),
                                    entry2.getValue());
                        }
                        ArrayList<Double> votesPrec = new ArrayList<>();
                        for (int i = TemporaryDB.startVotingAge(); i < TemporaryDB.getOldestAge(); i = i + dropDownSelection) {
                            votesPrec.add(calculatePercentage(allVotersHowVoteByAgeBlock.get(i), allVotersByAgeBlock.get(i)));
                        }
                        adapter = new DistributionOfVotersByAgeAdapter(this, agesBlocksList, votesPrec);
                        RV_DistributionByAge.setLayoutManager(new LinearLayoutManager(this));
                        RV_DistributionByAge.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void agesBlocksData() {
        int dropDownSelection = Integer.parseInt(ages_dropdown.getSelectedItem().toString());
        dbUtils.getAllAgeVoter(Constant.DataBaseName, Constant.VotersCollection, dropDownSelection, (result, t) -> {
            if (result != null) {
                Map<Integer, Integer> temp = (Map<Integer, Integer>) result;
                for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                    allVotersByAgeBlock.put(entry.getKey(),
                            entry.getValue());
                }
                dbUtils.getAllAgeVoterHowVote(Constant.DataBaseName, Constant.VotersCollection, dropDownSelection, (success, t2) -> {
                    if (success != null) {
                        Map<Integer, Integer> temp2 = (Map<Integer, Integer>) success;
                        for (Map.Entry<Integer, Integer> entry2 : temp2.entrySet()) {
                            allVotersHowVoteByAgeBlock.put(entry2.getKey(),
                                    entry2.getValue());
                        }
                        ArrayList<Double> votesPrec = new ArrayList<>();
                        for (int i = TemporaryDB.startVotingAge(); i < TemporaryDB.getOldestAge(); i = i + dropDownSelection) {
                            votesPrec.add(calculatePercentage(allVotersHowVoteByAgeBlock.get(i), allVotersByAgeBlock.get(i)));
                        }
                        adapter = new DistributionOfVotersByAgeAdapter(this, agesBlocksList, votesPrec);
                        RV_DistributionByAge.setLayoutManager(new LinearLayoutManager(this));
                        RV_DistributionByAge.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void getDataByArea() {
        dbUtils.getAllVotesInArea(Constant.DataBaseName, Constant.VotesCollectionNew, area, (result, t) -> {
            if (result != null) {
                dbUtils.getAllVotesInAreaAndSex(Constant.DataBaseName, Constant.VotesCollectionNew, area, Gender.זכר.toString(), (success, error) -> {
                    if (success != null) {
                        double boysGirlsVote = calculatePercentage((long) success, (long) result);
                        pb_by_sex_prec.setProgressPercentage(boysGirlsVote, true);
                    }
                });
                dbUtils.getAllVotersInArea(Constant.DataBaseName, Constant.VotersCollection, area, (success, error) -> {
                    if (success != null) {
                        double votNotVote = calculatePercentage((long) result, (long) success);
                        pb_voters_prec.setProgressPercentage(votNotVote, true);
                    }
                });
            }
        });
    }

    private void createAgeBlocks() {
        agesBlocksList = new ArrayList<AgesBlocks>();
        allVotersByAgeBlock = new HashMap<>();
        allVotersHowVoteByAgeBlock = new HashMap<>();
        int oldesAge = TemporaryDB.getOldestAge();
        int startVotingAge = TemporaryDB.startVotingAge();
        int dropDownSelection = Integer.parseInt(ages_dropdown.getSelectedItem().toString());
        while (startVotingAge < oldesAge) {
            allVotersByAgeBlock.put(startVotingAge, 0);
            allVotersHowVoteByAgeBlock.put(startVotingAge, 0);
            agesBlocksList.add(new AgesBlocks(startVotingAge, startVotingAge + dropDownSelection, dropDownSelection));
            startVotingAge += dropDownSelection;
        }
        if (adapter != null) {
            adapter.setAgesBlocksList(agesBlocksList);
            adapter.notifyDataSetChanged();
        }
    }

    private void findViews() {
        title = findViewById(R.id.title);
        pb_voters_prec = findViewById(R.id.pb_voters_prec);
        pb_by_sex_prec = findViewById(R.id.pb_by_sex_prec);
        pieChart = findViewById(R.id.pieChart_view);
        RV_DistributionByAge = findViewById(R.id.RV_DistributionByAge);
        ages_dropdown = findViewById(R.id.ages_dropdown);
        refreshList();
    }


    private void refreshList() {
        List<String> ages = Arrays.asList("3", "6", "9");

// Create an ArrayAdapter with the keysList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ages);

// Set the ArrayAdapter to the spinner
        ages_dropdown.setAdapter(adapter);
    }


    private void showPieChartAllCountry() {
        dbUtils.getResultAllCountry(Constant.DataBaseName, Constant.VotesCollectionNew, (success, error) -> {
            if (success != null) {
                Map<String, Integer> typeAmountMap = (Map<String, Integer>) success;
                makePie(typeAmountMap);
            }
        });
    }
    private void showPieChartByArea() {
        dbUtils.getResultByArea(Constant.DataBaseName, Constant.VotesCollectionNew, area, (success, error) -> {
            if (success != null) {
                Map<String, Integer> typeAmountMap = (Map<String, Integer>) success;
                makePie(typeAmountMap);
            }
        });
    }

    private void makePie(Map<String, Integer> typeAmountMap) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "מפלגות:";

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
        for (String type : typeAmountMap.keySet()) {
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
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

    public double calculatePercentage(long value, long total) {
        if (total == 0) {
            return 0;
        }
        double percentage = ((double) ((double) value / (double) total)) * 100.0;
        return percentage;
    }

    public double calculatePercentage(int value, int total) {
        if (total == 0) {
            return 0;
        }
        double percentage = ((double) ((double) value / (double) total)) * 100.0;
        return percentage;
    }
}