package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Activities.VoteActivity;
import com.example.finalproject.AdminsLogic.ResultActivity;
import com.example.finalproject.Calculations.Generators;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        resPage();
        button.setOnClickListener(v->
                resPage()
        );
        Generators.addVotersToDB();
        Generators.addPartiesToDB();
        Generators.addAreasToDB();
        Generators.addAdminToDB();
        Generators.initVotesCollection();
    }

    private void resPage() {
        Intent intent = new Intent(MainActivity.this, VoteActivity.class);
        startActivity(intent);
    }

    private void findView() {
    button = findViewById(R.id.button);
    }
}