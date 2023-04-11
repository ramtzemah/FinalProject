package com.example.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.R;

public class EndVote extends AppCompatActivity {
    private TextView partyNameTextView, approved_text;
    private ImageView partyLogoImageView;
    private Party chosenParty;
    private String partyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_vote);

        partyId = getIntent().getStringExtra("party_id");

        chosenParty = TemporaryDB.getPartyById(partyId);

        findViews();

        TemporaryDB.addVoteByPartyId(partyId);
        partyNameTextView.setText(chosenParty.getName());
        partyLogoImageView.setImageResource(chosenParty.getLogoResourceId());
    }

    private void findViews() {
        partyNameTextView = findViewById(R.id.party_name_textview);
        partyLogoImageView = findViewById(R.id.party_logo_imageview);
        approved_text = findViewById(R.id.approved_text);
    }
}