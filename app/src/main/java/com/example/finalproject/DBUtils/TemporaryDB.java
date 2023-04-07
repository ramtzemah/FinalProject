package com.example.finalproject.DBUtils;

import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;

import java.util.HashMap;
import java.util.Map;

public class TemporaryDB {
    private static Map<String, Voter> voters = new HashMap<>();
    private static Map<String, Party> parties = new HashMap<>();
    private static Map<String, Admin> admins = new HashMap<>();
    private static Map<String, Area> areas = new HashMap<>();

    public static void addVoter(Voter voter){
        voters.put(voter.getVoterId(),voter);
    }

    public static Map<String, Voter> getAllVoters(){
        return voters;
    }
    public static Map<String, Party> getAllParties(){
        return parties;
    }

    public static void addParty(Party party){
        parties.put(party.getPartyId(),party);
    }
    public static void addArea(Area area){
        areas.put(area.getId(), area);
    }

    public static Map<String, Admin> getAllAdmins(){
        return admins;
    }
    public static Map<String, Area> getAllAreas(){
        return areas;
    }

    public static int sunOfAllVoters(){
        return voters.size();
    }

    public static int snmOfAllParties(){
        return parties.size();
    }

    public static void manageAdmin(String voterId, String area){
            admins.put(voterId, new Admin(getVoterById(voterId), area, false));
    }

    private static Voter getVoterById(String voterId) {
        return voters.get(voterId);
    }

    public static void addAdminLeader(Admin admin) {
        admins.put(admin.getVoterId(), admin);
    }

    public static void fireAdmin(String voterId) {
        admins.remove(voterId);
    }
}
