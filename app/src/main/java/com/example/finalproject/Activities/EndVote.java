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

import com.example.finalproject.R;

public class EndVote extends AppCompatActivity {
    private TextView partyNameTextView, approved_text;
    private ImageView partyLogoImageView;
    private Party chosenParty;
    private String partyId;
    private DbUtils dbUtils;
    private String userId;
    private String areaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_vote);
        findViews();

        partyId = getIntent().getStringExtra("party_id");
        userId = getIntent().getStringExtra("userId");
        userId = "644bcbcfb29206472498446c";
        updateData();


        chosenParty = TemporaryDB.getPartyById(partyId);


        partyNameTextView.setText(chosenParty.getName());
        partyLogoImageView.setImageResource(chosenParty.getLogoResourceId());
    }

    private void updateData() {
        dbUtils.getVoteByPartyId(Constant.DataBaseName,Constant.VotesCollection,partyId,(result, t) -> {
            if (result != null) {
                Vote vote = (Vote) result;
                dbUtils.addVoteByPartyId(Constant.DataBaseName, Constant.VotesCollection, partyId, vote.getVotes() + 1);
                dbUtils.updateVoterHowAlreadyVote(Constant.DataBaseName, Constant.VotersCollection, userId);
                dbUtils.getVoterByVoterId(Constant.DataBaseName, Constant.VotersCollection, userId, (success, fail) -> {
                    if (success != null) {
                        Voter voter = (Voter) success;
                        areaName = voter.getCity();
                        updateAreaWithVoteByPartyIdAndAreaName(); //old
                        dbUtils.addNewVote(Constant.DataBaseName, Constant.VotesCollectionNew, new VoterVote(partyId, voter.getGender().toString(), voter.getAge(), areaName)); //new

                    }
                });
            }
        });
    }

    private void updateAreaWithVoteByPartyIdAndAreaName() {
        dbUtils.getAreaByAreaName(Constant.DataBaseName, Constant.AreasCollection, areaName,(result, t) -> {
            if (result !=null){
                Area area = (Area) result;
                area.getPartiesVotes().put(partyId, area.getPartiesVotes().get(partyId) + 1);
                dbUtils.updateAreaWithVotes(Constant.DataBaseName, Constant.AreasCollection, area);
            }});
    }

    private void findViews() {
        dbUtils = new DbUtils();
        partyNameTextView = findViewById(R.id.party_name_textview);
        partyLogoImageView = findViewById(R.id.party_logo_imageview);
        approved_text = findViewById(R.id.approved_text);
    }
}