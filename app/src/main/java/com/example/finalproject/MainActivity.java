package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Activities.LoginActivity;
import com.example.finalproject.Activities.SMSActivity;
import com.example.finalproject.Activities.VoteActivity;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
    private Button button,Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        findView();
        DbUtils dbUtils = new DbUtils();
        dbUtils.initConnection();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
        //resPage();

//        Generators.addVotersToDB2();
//        Generators.addPartiesToDB();
//        Generators.addAreasToDB();
//        Generators.addAdminToDB();
//        Generators.initVotesCollection();
        loginPage();
        button.setOnClickListener(v->
                resPage()
        );
        Login.setOnClickListener(v->
                loginPage()
        );
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
    Login = findViewById(R.id.Login);
    }
}