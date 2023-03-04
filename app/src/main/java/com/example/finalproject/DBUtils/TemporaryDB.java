package com.example.finalproject.DBUtils;

import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;

import java.util.HashMap;
import java.util.Map;

public class TemporaryDB {
    private static Map<String, Voter> voters = new HashMap<>();
    private static Map<String, Party> parties = new HashMap<>();
    private static Map<String, Admin> admins = new HashMap<>();

    public static void addVoter(Voter voter){
        voters.put(voter.getVoterId(),voter);
    }

    public static Map<String, Voter> getAllVoters(){
        return voters;
    }

    public static void addParty(Party party){
        parties.put(party.getPartyId(),party);
    }

    public static Map<String, Admin> getAllAdmins(){
        return admins;
    }

    public static int getAllVotersNumber(){
        return voters.size();
    }

    public static int getAllPartiesNumber(){
        return parties.size();
    }

    public static void manageAdmin(Admin admin, String voterId, String area){
        if(admin.isAdminLeader()){
            admins.put(voterId, new Admin(getVoterById(voterId), area, false));
        }
    }

    private static Voter getVoterById(String voterId) {
        return voters.get(voterId);
    }

    public static void addAdminLeader(Admin admin) {
        admins.put(admin.getVoterId(), admin);
    }
}
