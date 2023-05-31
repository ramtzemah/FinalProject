package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Activities.LoginActivity;
import com.example.finalproject.Activities.SMSActivity;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.DBUtils.initDb;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        findView();


        DbUtils dbUtils = new DbUtils();
        dbUtils.initConnection();
//        initDbMethod();

        regularFlow();

        button.setOnClickListener(v->
                resPage()
        );

        // TODO
//        1. id number to real one
//        2. vote once

    }

    private void regularFlow() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TemporaryDB.setOldestAge("oldestAge");
        TemporaryDB.setStartVotingAge("startAge");
        TemporaryDB.dateOfStartVotingBeforeFormat();
        TemporaryDB.dateOfEndVotingBeforeFormat();
        TemporaryDB.addAllParties();
        TemporaryDB.addAllAreas();
        loginPage();
    }

    private void initDbMethod() {
                try {
            initDb initDb = new initDb();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginPage() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void resPage() {
        Intent intent = new Intent(MainActivity.this, SMSActivity.class);
        startActivity(intent);
    }

    private void findView() {
        button = findViewById(R.id.button);
    }
}