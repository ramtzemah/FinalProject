package com.example.finalproject.Entities;

import com.example.finalproject.Calculations.Generators;

public class Voter {
    private String voterId; // maybe not necessary
    private String firstName;
    private String lastName;
    private int age;
    private Enum Gender;
    private String City;
    private boolean alreadyVote;

    public Voter(String firstName, String lastName, int age, Enum gender, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        Gender = gender;
        City = city;
        setVoterId(Generators.generateId());
    }

    private void setVoterId(String generateId) {
        this.voterId = generateId;
    }

    public String getVoterId() {
        return voterId;
    }

    public boolean isAlreadyVote() {
        return alreadyVote;
    }

    public void setAlreadyVote(boolean alreadyVote) {
        this.alreadyVote = alreadyVote;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "id='" + voterId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", Gender=" + Gender +
                ", City='" + City + '\'' +
                ", alreadyVote=" + alreadyVote +
                '}';
    }
}
