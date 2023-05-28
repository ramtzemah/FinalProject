package com.example.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Vote;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Entities.VoterVote;
import com.airbnb.lottie.LottieAnimationView;

import com.example.finalproject.R;

public class EndVote extends AppCompatActivity {
    private TextView partyNameTextView, approved_text;
    private ImageView partyLogoImageView;
    private Party chosenParty;
    private String partyId;
    private DbUtils dbUtils;
    private String userId;
    private String areaName;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_vote);
        findViews();

        //partyId = getIntent().getStringExtra("party_id");
        //userId = getIntent().getStringExtra("userId");
//        userId = "644bcbcfb29206472498446c";
        //updateData();


        //chosenParty = TemporaryDB.getPartyById(partyId);


       //partyNameTextView.setText(chosenParty.getName());
        //partyLogoImageView.setImageResource(chosenParty.getLogoResourceId());
    }

    private void updateData() {
                dbUtils.updateVoterHowAlreadyVote(Constant.DataBaseName, Constant.VotersCollection, userId);
                dbUtils.getVoterByVoterId(Constant.DataBaseName, Constant.VotersCollection, userId, (success, fail) -> {
                    if (success != null) {
                        Voter voter = (Voter) success;
                        areaName = voter.getArea();
                        dbUtils.addNewVote(Constant.DataBaseName, Constant.VotesCollectionNew, new VoterVote(partyId, voter.getGender().toString(), voter.getAge(), areaName)); //new

                    }
                });
    }

    private void findViews() {
        dbUtils = new DbUtils();
        partyNameTextView = findViewById(R.id.party_name_textview);
        partyLogoImageView = findViewById(R.id.party_logo_imageview);
        approved_text = findViewById(R.id.approved_text);
        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.playAnimation();

    }
}