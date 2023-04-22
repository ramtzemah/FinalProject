package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Activities.VoteActivity;
import com.example.finalproject.AdminsLogic.ResultActivity;
import com.example.finalproject.DBUtils.TemporaryDB;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
    private Button button,Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        findView();

//        try {
//            initDb initDb = new initDb();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        TemporaryDB.addAllVoters();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TemporaryDB.addAllParties();
        TemporaryDB.addAllAreas();
        TemporaryDB.addAllAdmins();
        //resPage();

//        Generators.addVotersToDB2();
//        Generators.addPartiesToDB();
//        Generators.addAreasToDB();
//        Generators.addAdminToDB();
//        Generators.initVotesCollection();
        button.setOnClickListener(v->
                resPage()
        );
        Login.setOnClickListener(v->
                loginPage()
        );
    }

    private void loginPage() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        startActivity(intent);
    }

    private void resPage() {
        Intent intent = new Intent(MainActivity.this, VoteActivity.class);
        startActivity(intent);
    }

    private void findView() {
    button = findViewById(R.id.button);
    Login = findViewById(R.id.Login);
    }
}