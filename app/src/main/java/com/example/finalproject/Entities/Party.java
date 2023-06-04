package com.example.finalproject.Entities;

import org.bson.Document;
import java.util.UUID;

public class Party {
    private String partyId; // maybe not necessary
    private String name;
    private String agenda;
    private int logoResourceId;
    public Party(String name, String agenda) {
        this.name = name;
        this.agenda = agenda;
    }
    public Party(String name, int logoResourceId, String agenda) {
        this.name = name;
        this.logoResourceId = logoResourceId;
        this.agenda = agenda;
        String result = UUID.nameUUIDFromBytes(name.getBytes()).toString();
        this.partyId = result;
    }

    public Party(Document document) {
        this.partyId = document.getString("partyId");
        this.name = document.getString("name");
        this.agenda = document.getString("agenda");
        this.logoResourceId = document.getInteger("logoResourceId");
    }

    public String getPartyId() {
        return partyId;
    }

    public String getName() {
        return name;
    }

    public int getLogoResourceId() {
        return logoResourceId;
    }

    public String getAgenda() {
        return agenda;
    }
}
