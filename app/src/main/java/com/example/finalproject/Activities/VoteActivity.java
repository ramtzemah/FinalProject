package com.example.finalproject.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.finalproject.AdminsLogic.ManageSection;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryFB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;
import com.tsuryo.androidcountdown.Counter;
import com.tsuryo.androidcountdown.TimeUnits;

import java.util.Calendar;
import java.util.Date;

public class VoteActivity extends AppCompatActivity {
    private MaterialButton MB_voteBtn, MB_manageBtn, MB_partyPlatformBtn;
    private String voterId;
    private String adminId;
    private boolean isAdmin = false;
    private DbUtils dbUtils;
    private Admin tempAdmin;
    private Counter mCounter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);
        findViews();
        voterId = getIntent().getStringExtra("VoterId");

        handleAdminFlow();

        setCounter();
        setButtons();
    }

    private void setCounter() {
        String formattedDate = TemporaryFB.dateOfStartVotingAfterFormat();
        mCounter.setDate(formattedDate); //countdown starts

        mCounter.setIsShowingTextDesc(true);
        mCounter.setTextColor(R.color.black);
        mCounter.setMaxTimeUnit(TimeUnits.HOUR);
        mCounter.setTextSize(130);
        mCounter.setTypeFace(ResourcesCompat.getFont(this, com.tsuryo.androidcountdown.R.font.digi));

        mCounter.setListener(new Counter.Listener() {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: Counter - " + millisUntilFinished);
            }

            @Override
            public void onTick(long days, long hours, long minutes, long seconds) {
                Log.d(TAG, "onTick: Counter - " + days + "d " +
                        hours + "h " +
                        minutes + "m " +
                        seconds + "s ");
            }
        });
    }

    private void handleAdminFlow() {
        dbUtils.getAdminByVoterId(Constant.DataBaseName, Constant.AdminsCollection, voterId, (success, error) -> {
            if (success != null) {
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
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.before(TemporaryFB.dateOfStartVotingBeforeFormat())) {
            MB_voteBtn.setEnabled(false);
            MB_voteBtn.setBackgroundResource(R.drawable.btn_grey);
        } else {
            MB_voteBtn.setEnabled(true);
            MB_voteBtn.setBackgroundResource(R.drawable.btn_regular);
        }
        MB_voteBtn.setOnClickListener(v -> toAllParties());
        MB_manageBtn.setOnClickListener(v -> toAdminManageSection());
        MB_partyPlatformBtn.setOnClickListener(v -> toPartiesPlatform());
    }

    private void toPartiesPlatform() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        intent.putExtra("from", "platform");
        intent.putExtra("userId", voterId);
        startActivity(intent);
    }

    private void toAllParties() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        intent.putExtra("from", "vote");
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
        mCounter = findViewById(R.id.counter);
        MB_voteBtn = findViewById(R.id.MB_voteBtn);
        MB_manageBtn = findViewById(R.id.MB_manageBtn);
        MB_partyPlatformBtn = findViewById(R.id.MB_partyPlatformBtn);
        dbUtils = new DbUtils();
    }
}
