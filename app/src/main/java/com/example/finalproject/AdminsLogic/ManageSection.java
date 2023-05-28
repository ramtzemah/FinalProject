package com.example.finalproject.AdminsLogic;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;
import com.tsuryo.androidcountdown.Counter;
import com.tsuryo.androidcountdown.TimeUnits;

import java.util.Calendar;
import java.util.Date;

public class ManageSection extends AppCompatActivity {
    private MaterialButton MB_appointAdminBtn;
    private MaterialButton MB_firedAdminBtn;
    private MaterialButton MB_resultBtn;
    private String voterId, area;
    private boolean isAdminLeader = false;
    private Counter mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_section);
        voterId = getIntent().getStringExtra("voterId");
        area = getIntent().getStringExtra("area");
        isAdminLeader = getIntent().getBooleanExtra("isAdminLeader", false);
        findViews();
        viewForAdminLeader();
        setCounter();
        setButtons();
    }

    private void setCounter() {
        String formattedDate = TemporaryDB.dateOfEndVotingAfterFormat();
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

    private void viewForAdminLeader() {
        if (isAdminLeader) {
            MB_appointAdminBtn.setVisibility(View.VISIBLE);
            MB_firedAdminBtn.setVisibility(View.VISIBLE);
            TemporaryDB.addAllAdmins();
        }
    }

    private void setButtons() {
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.before(TemporaryDB.endDesiredDate)) {
            MB_resultBtn.setEnabled(false);
//            MB_resultBtn.setBackgroundResource(R.drawable.btn_grey);
        } else {
            MB_resultBtn.setEnabled(true);
//            MB_resultBtn.setBackgroundResource(R.drawable.btn_regular);
        }
        MB_resultBtn.setOnClickListener(v -> results());
        MB_firedAdminBtn.setOnClickListener(v -> firedAdmin());
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
    }

    private void results() {
        Intent intent = new Intent(ManageSection.this, ResultActivity.class);
        intent.putExtra("area", area);
        startActivity(intent);
        finish();
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
        mCounter = findViewById(R.id.counter);
    }
}