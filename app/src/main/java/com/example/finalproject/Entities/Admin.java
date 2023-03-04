package com.example.finalproject.Entities;

public class Admin extends Voter{

    private String area;
    private boolean isAdminLeader;

    private Voter voter;

    public Admin(String firstName, String lastName, int age, Enum gender, String city, int idNumber, String phoneNumber, String area, boolean isAdminLeader) {
        super(firstName, lastName, age, gender, city, idNumber, phoneNumber);
        this.area = area;
        this.isAdminLeader = isAdminLeader;
    }

    public Admin(Voter voter, String area, boolean isAdminLeader) {
        super(voter.getFirstName(), voter.getLastName(), voter.getAge(), voter.getGender(), voter.getCity(), voter.getIdNumber(), voter.getPhoneNumber());
        this.area = area;
        this.isAdminLeader = isAdminLeader;
    }


    public boolean isAdminLeader() {
        return isAdminLeader;
    }
}
