package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.AdminsLogic.ManageSection;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class VoteActivity extends AppCompatActivity {
    private MaterialButton MB_voteBtn;
    private MaterialButton MB_manageBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);
        findViews();
        setButtons();
    }

    private void setButtons() {
        MB_voteBtn.setOnClickListener(v -> toAllParties());
        MB_manageBtn.setOnClickListener(v -> toAdminManageSection());
    }

    private void toAllParties() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        startActivity(intent);
    }
    private void toAdminManageSection() {
        Intent intent = new Intent(VoteActivity.this, ManageSection.class);
        startActivity(intent);
    }

    private void findViews() {
        MB_voteBtn = findViewById(R.id.MB_voteBtn);
        MB_manageBtn = findViewById(R.id.MB_manageBtn);
    }
}
