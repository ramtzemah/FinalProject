package com.example.finalproject.Entities;

import com.example.finalproject.Calculations.Generators;

public class Admin extends Voter{
    private String id;
    private String area;
    private boolean isAdminLeader;

    public Admin(String firstName, String lastName, int age, Enum gender, String city, int idNumber, String phoneNumber, String area, boolean isAdminLeader) {
        super(firstName, lastName, age, gender, city, idNumber, phoneNumber);
        this.area = area;
        this.isAdminLeader = isAdminLeader;
        id = Generators.generateId();
    }

    public Admin(Voter voter, String area, boolean isAdminLeader) {
        super(voter.getVoterId(), voter.getFirstName(), voter.getLastName(), voter.getAge(), voter.getGender(), voter.getCity(), voter.getIdNumber(), voter.getPhoneNumber());
        this.area = area;
        this.isAdminLeader = isAdminLeader;
        this.id = voter.getVoterId();
    }


    public boolean isAdminLeader() {
        return isAdminLeader;
    }

    public String getArea() {
        return area;
    }

    public String getId() {
        return id;
    }
}
