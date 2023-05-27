package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.DBUtils.initDb;
import com.example.finalproject.R;

import io.realm.Realm;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 2000; // 2 seconds

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        Realm.init(this);

        DbUtils dbUtils = new DbUtils();
        dbUtils.initConnection();
    //    initDbMethod();

        regularFlow();
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        // Simulate loading for 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                moveToWelcomeActivity();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    private void initDbMethod() {
        try {
            initDb initDb = new initDb();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void regularFlow() {
        TemporaryDB.setOldestAge("oldestAge");
        TemporaryDB.setStartVotingAge("startAge");
        TemporaryDB.dateOfStartVotingBeforeFormat();
        TemporaryDB.dateOfEndVotingBeforeFormat();
        TemporaryDB.addAllParties();
        TemporaryDB.addAllAreas();
    }

    private void moveToWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
