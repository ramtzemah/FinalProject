package com.example.finalproject.Entities;
import org.bson.Document;

import java.util.Date;

public class Token {
    private String tokenId;
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

    public Token(Document document) {
        this.tokenId = document.getString("tokenId");
        this.voterId = document.getString("voterId");
        this.token = document.getString("token");
        this.sentDate = document.getDate("sentDate");
        this.expirationDate = document.getDate("expirationDate");
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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
