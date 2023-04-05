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
    private int idNumber;
    private String phoneNumber;

    public Voter(String firstName, String lastName, int age, Enum gender, String city, int idNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.Gender = gender;
        this.City = city;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        setVoterId(Generators.generateId());
    }

    public Voter(String voterId,String firstName, String lastName, int age, Enum gender, String city, int idNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.Gender = gender;
        this.City = city;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        setVoterId(voterId);
    }

    public Voter() {
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Enum getGender() {
        return Gender;
    }

    public String getCity() {
        return City;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
