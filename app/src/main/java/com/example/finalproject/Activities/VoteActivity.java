package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.AdminsLogic.ManageSection;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class VoteActivity extends AppCompatActivity {
    private MaterialButton MB_voteBtn,MB_manageBtn,MB_partyPlatformBtn;
    private String voterId;
    private String adminId;
    private boolean isAdmin = false;
    private DbUtils dbUtils;
    private Admin tempAdmin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);
        findViews();
        voterId = getIntent().getStringExtra("VoterId");
        try {
            handleAdminFlow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setButtons();
//        voterId = "644bcbcfb292064724984468";


    }

    private void handleAdminFlow() {
        dbUtils.getAdminByVoterId(Constant.DataBaseName, Constant.AdminsCollection, voterId, (success, error) -> {
            if(success != null){
                tempAdmin = (Admin) success;
                adminId = tempAdmin.getVoterId();
                isAdmin = true;
                setInvisible();
            }
        });
    }

    private void setInvisible() {
        MB_manageBtn.setVisibility(View.VISIBLE);
    }

    private void setButtons() {
        MB_voteBtn.setOnClickListener(v -> toAllParties());
        MB_manageBtn.setOnClickListener(v -> toAdminManageSection());
        MB_partyPlatformBtn.setOnClickListener(v->toPartiesPlatform());
    }

    private void toPartiesPlatform() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        intent.putExtra("from","platform");
        intent.putExtra("userId", voterId);
        startActivity(intent);
    }

    private void toAllParties() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        intent.putExtra("from","vote");
        intent.putExtra("userId", voterId);
        startActivity(intent);
    }
    private void toAdminManageSection() {
        Intent intent = new Intent(VoteActivity.this, ManageSection.class);
        intent.putExtra("voterId", voterId);
        intent.putExtra("area", tempAdmin.getArea());
        intent.putExtra("isAdminLeader", tempAdmin.isAdminLeader());
        startActivity(intent);
    }

    private void findViews() {
        MB_voteBtn = findViewById(R.id.MB_voteBtn);
        MB_manageBtn = findViewById(R.id.MB_manageBtn);
        MB_partyPlatformBtn = findViewById(R.id.MB_partyPlatformBtn);
        dbUtils = new DbUtils();
    }
}
