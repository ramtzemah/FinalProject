package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class ManageSection extends AppCompatActivity {
    private MaterialButton MB_appointAdminBtn;
    private MaterialButton MB_firedAdminBtn;
    private MaterialButton MB_resultBtn;
    private String voterId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_section);
        voterId = getIntent().getStringExtra("voterId");

        findViews();
        setButtons();
        TemporaryDB.addAllAdmins();
    }

    private void setButtons() {
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
        MB_firedAdminBtn.setOnClickListener(v -> firedAdmin());
        MB_resultBtn.setOnClickListener(v -> results());
    }

    private void results() {
        Intent intent = new Intent(ManageSection.this, ResultActivity.class);
        String areaToPass = getAdminArea();
        intent.putExtra("area",areaToPass);
        startActivity(intent);
        finish();
    }

    private String getAdminArea() {
        Admin admin = TemporaryDB.getAllAdmins().get(voterId);
        if(admin.getArea() != " "){
            return admin.getArea();
        }
        return "all";
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
        MB_resultBtn = findViewById(R.id.MB_resultBtn);
    }
}