package com.example.finalproject.Entities;

import android.util.Log;

import com.example.finalproject.Calculations.Generators;
import org.bson.Document;

import java.util.ArrayList;

public class Admin extends Voter{
    private String voterId;
    private String area;
    private boolean isAdminLeader;

    public Admin(String firstName, String lastName, int age, Enum gender, int idNumber, String phoneNumber, String area, boolean isAdminLeader) {
        super(firstName, lastName, age, gender, area, idNumber, phoneNumber);
        this.area = area;
        this.isAdminLeader = isAdminLeader;
        voterId = Generators.generateId();
    }

    public Admin(Voter voter, String area, boolean isAdminLeader) {
        super(voter.getVoterId(), voter.getFirstName(), voter.getLastName(), voter.getAge(), voter.getGender(), voter.getArea(), voter.getIdNumber(), voter.getPhoneNumber());
        this.area = area;
        this.isAdminLeader = isAdminLeader;
        this.voterId = voter.getVoterId();
    }

    public Admin(String voterId, Document doc, Voter voter) {
        super(voter.getVoterId(), voter.getFirstName(), voter.getLastName(), voter.getAge(), voter.getGender(), voter.getArea(), voter.getIdNumber(), voter.getPhoneNumber());
        this.voterId = voterId;
        this.area = doc.getString("area");
        this.isAdminLeader = doc.getBoolean("isAdminLeader");
    }
    public Admin(Document document) {
        this.voterId = document.getString("voterId");
        this.area = document.getString("area");
        this.isAdminLeader = document.getBoolean("isAdminLeader");
    }

    public Admin(Document adminSide, ArrayList<Document> voterSide) {
        super(voterSide.get(0));
        this.voterId = adminSide.getString("voterId");
        this.area = adminSide.getString("area");
        this.isAdminLeader = adminSide.getBoolean("isAdminLeader");
        Log.d("puuu", "dds");
    }

    public boolean isAdminLeader() {
        return isAdminLeader;
    }

    public String getArea() {
        return area;
    }

    public String getVoterId() {
        return voterId;
    }
}
