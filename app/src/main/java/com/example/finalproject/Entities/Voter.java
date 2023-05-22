package com.example.finalproject.Entities;

import android.util.Log;
import org.bson.Document;
import org.bson.types.ObjectId;


public class Voter {
    private String voterId; // maybe not necessary
    private String firstName;
    private String lastName;
    private int age;
    private Enum Gender;
    private String area;
    private boolean alreadyVote;
    private int idNumber;
    private String phoneNumber;

    public Voter(String firstName, String lastName, int age, Enum gender, String area, int idNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.Gender = gender;
        this.area = area;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        ObjectId objectId = new ObjectId();
        setVoterId(objectId.toString());
    }

    public Voter(String voterId,String firstName, String lastName, int age, Enum gender, String area, int idNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.Gender = gender;
        this.area = area;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        setVoterId(voterId);
    }

    public Voter() {
    }

    public Voter(Document document) {
        Log.d("ptttt", " " +document);
        this.voterId = document.getString("voterId");
        this.firstName = document.getString("firstName");
        this.lastName = document.getString("lastName");
        this.age = document.getInteger("age");
        this.Gender = Gender.valueOf(com.example.finalproject.Enums.Gender.class, document.getString("gender").toUpperCase());
        this.area = document.getString("area");
        this.alreadyVote = document.getBoolean("alreadyVote");
        this.idNumber = document.getInteger("idNumber");
        this.phoneNumber = document.getString("phoneNumber");
    }

    public void setVoterId(String generateId) {
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

    public String getArea() {
        return area;
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
                ", area='" + area + '\'' +
                ", alreadyVote=" + alreadyVote +
                '}';
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Enum gender) {
        Gender = gender;
    }

    public void setArea(String city) {
        area = area;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
