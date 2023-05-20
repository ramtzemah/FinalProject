package com.example.finalproject.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapters.PartyAdapter;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class AllParties extends AppCompatActivity {
    private RecyclerView RV_parties;
    private PartyAdapter adapter;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_parties);
        String source = getIntent().getStringExtra("from");
        userId = getIntent().getStringExtra("userId");
        findViews();
        adapter = new PartyAdapter(this, TemporaryDB.getAllParties(),source, userId);
        RV_parties.setLayoutManager(new LinearLayoutManager(this));
        RV_parties.setAdapter(adapter);
    }

    private void findViews() {
        RV_parties = findViewById(R.id.RV_parties);
    }

}
