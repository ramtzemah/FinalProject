package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalproject.Activities.AllParties;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class ManageSection extends AppCompatActivity {
    private MaterialButton MB_appointAdminBtn;
    private MaterialButton MB_firedAdminBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_section);
        findViews();
        setButtons();
    }

    private void setButtons() {
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
        MB_firedAdminBtn.setOnClickListener(v -> firedAdmin());
    }

    private void appointAdmin() {
        Intent intent = new Intent(ManageSection.this, AppointAdmin.class);
        startActivity(intent);
    }
    private void firedAdmin() {
        Intent intent = new Intent(ManageSection.this, FireAdmin.class);
        startActivity(intent);
    }

    private void findViews() {
        MB_appointAdminBtn = findViewById(R.id.MB_appointAdminBtn);
        MB_firedAdminBtn = findViewById(R.id.MB_firedAdminBtn);
    }
}