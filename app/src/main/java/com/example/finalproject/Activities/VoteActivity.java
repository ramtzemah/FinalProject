package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class VoteActivity extends AppCompatActivity {
    private MaterialButton MB_votebtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);
        findViews();
        setbuttons();
    }

    private void setbuttons() {
        MB_votebtn.setOnClickListener(v -> toAllParties());
    }

    private void toAllParties() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        startActivity(intent);
    }

    private void findViews() {
        MB_votebtn = findViewById(R.id.MB_votebtn);
    }
}
