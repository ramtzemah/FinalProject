package com.example.finalproject.Entities;

import org.bson.Document;

public class Vote {
    int votes;
    String partyId;

    public Vote(int votes, String partyId) {
        this.votes = votes;
        this.partyId = partyId;
    }

    public Vote(Document document) {
        this.partyId = document.getString("partyId");
        this.votes = document.getInteger("votes");
    }

    public void addVotes() {
        this.votes += 1;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
