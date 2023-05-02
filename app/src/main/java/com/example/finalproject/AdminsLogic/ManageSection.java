package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.finalproject.Activities.AllParties;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class ManageSection extends AppCompatActivity {
    private MaterialButton MB_appointAdminBtn;
    private MaterialButton MB_firedAdminBtn;
    private LottieAnimationView managerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_section);
        findViews();
        setButtons();
        //TemporaryDB.addAllAdmins();
    }

    private void setButtons() {
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
        MB_firedAdminBtn.setOnClickListener(v -> firedAdmin());
    }

    private void appointAdmin() {
        Intent intent = new Intent(ManageSection.this, AppointAdmin.class);
        startActivity(intent);
        finish();
    }
    private void firedAdmin() {
        Intent intent = new Intent(ManageSection.this, FireAdmin.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        MB_appointAdminBtn = findViewById(R.id.MB_appointAdminBtn);
        MB_firedAdminBtn = findViewById(R.id.MB_firedAdminBtn);
        managerview = findViewById(R.id.managerview);
        managerview.setRepeatCount(LottieDrawable.INFINITE);
        managerview.playAnimation();
    }
}