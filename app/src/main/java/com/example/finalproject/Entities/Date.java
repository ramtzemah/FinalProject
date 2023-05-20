package com.example.finalproject.Entities;

import android.util.Log;

import androidx.annotation.NonNull;

import org.bson.Document;

import java.util.HashMap;

public class Date {
    private double year;
    private double month;
    private double day;
    private double hour;
    private double minute;
    private double secund;

    public Date(){
    }

    public Date(@NonNull Document document) {
        this.year = document.getDouble("year");
        this.month = document.getDouble("month");
        this.day = document.getDouble("day");
        this.hour = document.getDouble("hour");
        this.minute = document.getDouble("minute");
        this.secund = document.getDouble("second");
    }

    public int getYear() {
        return (int) year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return (int) month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return (int) day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return (int) hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return (int) minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecund() {
        return (int) secund;
    }

    public void setSecund(int secund) {
        this.secund = secund;
    }
}
