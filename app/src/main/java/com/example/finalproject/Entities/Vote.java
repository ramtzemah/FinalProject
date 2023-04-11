package com.example.finalproject.Entities;

public class Vote {
    int votes;

    public Vote(int votes) {
        this.votes = votes;
    }

    public void addVotes() {
        this.votes += 1;
    }
}
