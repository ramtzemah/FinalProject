package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.Activities.AllParties;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class ManageSection extends AppCompatActivity {
    private MaterialButton MB_appointAdminBtn;
    private MaterialButton MB_firedAdminBtn;
    private MaterialButton MB_resultBtn;
    private String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_section);
        adminId = getIntent().getStringExtra("admin_id");
        findViews();
        setButtons();
    }

    private void setButtons() {
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
        MB_firedAdminBtn.setOnClickListener(v -> firedAdmin());
        MB_resultBtn.setOnClickListener(v -> result());
    }

    private void result() {
        Intent intent = new Intent(ManageSection.this, ResultActivity.class);
        intent.putExtra("admin_id",adminId);
        startActivity(intent);
    }
    private void appointAdmin() {
        Intent intent = new Intent(ManageSection.this, AppointAdmin.class);
        intent.putExtra("admin_id",adminId);
        startActivity(intent);
    }
    private void firedAdmin() {
        Intent intent = new Intent(ManageSection.this, FireAdmin.class);
        intent.putExtra("admin_id",adminId);
        startActivity(intent);
    }

    private void findViews() {
        MB_appointAdminBtn = findViewById(R.id.MB_appointAdminBtn);
        MB_firedAdminBtn = findViewById(R.id.MB_firedAdminBtn);
        MB_resultBtn = findViewById(R.id.MB_resultBtn);
    }
}