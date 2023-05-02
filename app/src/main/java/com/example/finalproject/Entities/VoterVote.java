package com.example.finalproject.Entities;

import android.util.Log;

import com.example.finalproject.Enums.Gender;

import org.bson.Document;

public class VoterVote {
    private String partyId;
    private String gender;
    private int age;
    private String area;

    public VoterVote(String partyId, String gender, int age, String area) {
        this.partyId = partyId;
        this.gender = gender;
        this.age = age;
        this.area = area;
    }

    public VoterVote() {
    }

    public VoterVote(Document document) {
        Log.d("ptttt", " " +document);
        this.partyId = document.getString("partyId");
        this.area = document.getString("area");
        this.age = document.getInteger("age");
        this.gender = document.getString("gender");
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
