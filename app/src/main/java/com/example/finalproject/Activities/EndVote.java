package com.example.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageView thanksForVoting;
    private Party chosenParty;
    private String partyId;
    private DbUtils dbUtils;
    private String userId, areaName;
    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_vote);
        findViews();

        partyId = getIntent().getStringExtra("party_id");
        userId = getIntent().getStringExtra("userId");
//        userId = "644bcbcfb29206472498446c";
        updateData();


        chosenParty = TemporaryDB.getPartyById(partyId);

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
        animationView = findViewById(R.id.animationView);
        animationView.playAnimation();
        thanksForVoting = findViewById(R.id.thanksForVoting);
    }
}