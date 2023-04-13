package com.example.finalproject.Entities;
import java.util.Date;

public class Token {
    private String voterId;
    private String token;
    private Date sentDate;
    private Date expirationDate;

    public Token(String voterId, String token, Date sentDate) {
        this.voterId = voterId;
        this.token = token;
        this.sentDate = sentDate;
        this.expirationDate = new Date(sentDate.getTime() + 5 * 60 * 1000); // Add 5 minutes to the sentDate
    }

    // Getters and setters
    public String getVoterId() {
        return voterId;
    }

    public String getToken() {
        return token;
    }



    public Date getExpirationDate() {
        return expirationDate;
    }


    @Override
    public String toString() {
        return "Token{" +
                "voterId='" + voterId + '\'' +
                ", token='" + token + '\'' +
                ", sentDate=" + sentDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
