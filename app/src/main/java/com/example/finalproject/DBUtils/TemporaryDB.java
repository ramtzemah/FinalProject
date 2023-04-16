package com.example.finalproject.DBUtils;
import com.example.finalproject.Calculations.Generators;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Token;
import com.example.finalproject.Entities.Vote;
import com.example.finalproject.Entities.Voter;

import java.util.HashMap;
import java.util.Map;

public class TemporaryDB {
    private static Map<String, Voter> voters = new HashMap<>();
    private static Map<String, Party> parties = new HashMap<>();
    private static Map<String, Admin> admins = new HashMap<>();
    private static Map<String, Vote> votes = new HashMap<>();
    private static Map<String, Area> areas = new HashMap<>();
    private static Map<String, Token> tokens = new HashMap<>();


    public static void addVoter(Voter voter){
        voters.put(voter.getVoterId(),voter);
    }

    public static Map<String, Voter> getAllVoters(){
        return voters;
    }
    public static Map<String, Party> getAllParties(){
        return parties;
    }
    public static Map<String, Token> getAllTokens(){
        return tokens;
    }
    public static void addToken(Token token){
        tokens.put(Generators.generateRandomString(),token);
    }
    public static void addParty(Party party){
        parties.put(party.getPartyId(),party);
    }
    public static void InitVotes(Party party){
        votes.put(party.getPartyId() ,new Vote(0));
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

    public static Voter getVoterById(String voterId) {
        return voters.get(voterId);
    }
    public static Party getPartyById(String partyId) {
        return parties.get(partyId);
    }

    public static void addAdminLeader(Admin admin) {
        admins.put(admin.getVoterId(), admin);
    }
    public static void fireAdmin(String voterId) {
        admins.remove(voterId);
    }

    public static void addVoteByPartyId(String partyId) {
        votes.get(partyId).addVotes();
    }
    public static String getPhoneNumberById(String id) {
        Map<String, Voter> voters = TemporaryDB.getAllVoters();
        for (Voter voter : voters.values()) {
            if (voter.getIdNumber() == Integer.valueOf(id)) {
                return voter.getPhoneNumber();
            }
        }
        return "";
    }
    public static Token getToken(String tocheck) {

        for (Token token : TemporaryDB.getAllTokens().values()) {
            if (token.getToken().equals(tocheck)) {
                return token;
            }
        }
        return null;
    }

}
