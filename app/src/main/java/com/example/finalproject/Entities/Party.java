package com.example.finalproject.Entities;

import com.example.finalproject.Calculations.Generators;

public class Party {
    private String partyId; // maybe not necessary
    private String name;
    private String agenda;

    public Party(String name, String agenda) {
        this.name = name;
        this.agenda = agenda;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String id) {
        this.partyId = Generators.generateId();
    }
}
