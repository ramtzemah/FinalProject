package com.example.finalproject.DBUtils;
import android.util.Log;

import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.Calculations.Generators;
import com.example.finalproject.Callbacks.AdminsCallback;
import com.example.finalproject.Callbacks.AreasCallback;
import com.example.finalproject.Callbacks.PartiesCallback;
import com.example.finalproject.Callbacks.VotersCallback;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Token;
import com.example.finalproject.Entities.Vote;
import com.example.finalproject.Entities.Voter;

import java.util.HashMap;
import java.util.List;
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
    public static DbUtils dbUtils = new DbUtils();


    public static void addAllVoters(){
        dbUtils.getAllVoters(Constant.DataBaseName, Constant.VotersCollection, (VotersCallback) (result, error)-> {
            if (error != null) {
                Log.d("ptttt", "errorrrrr");
                // Handle the error.
            } else {
                List<Voter> voters1 = (List<Voter>) result;
                for (Voter v : voters1){
                    voters.put(v.getVoterId(), v);
                }
            }
        }
        );
    }

    public static void addAllParties() {
        dbUtils.getAllParties(Constant.DataBaseName, Constant.PartiesCollection, (PartiesCallback) (result, error)-> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        List<Party> parties1 = (List<Party>) result;
                        for (Party p : parties1){
                            parties.put(p.getPartyId(), p);
                        }
                    }
                }
        );
    }

    public static void addAllAreas() {
        dbUtils.getAllAreas(Constant.DataBaseName, Constant.AreasCollection, (AreasCallback) (result, error)-> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        List<Area> areas1 = (List<Area>) result;
                        for (Area a : areas1){
                            areas.put(a.getId(), a);
                        }
                    }
                }
        );
    }

    public static void addAllAdmins() {
        dbUtils.getAllAdmins(Constant.DataBaseName, Constant.AdminsCollection, (AdminsCallback) (result, error)-> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        admins = new HashMap<>();
                        List<Admin> admins1 = (List<Admin>) result;
                        for (Admin a : admins1){
                            admins.put(a.getVoterId(), a);
                        }
                    }
                }
        );
    }

    public static void addToken(Token token){
        tokens.put(Generators.generateRandomString(),token);
    }
    public static void addParty(Party party){
        parties.put(party.getPartyId(),party);
    }
    public static void InitVotes(Party party){
        votes.put(Generators.generateId() ,new Vote(0, party.getPartyId()));
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

    public static void manageAdmin(String voterId, String area, Voter voter){
            admins.put(voterId, new Admin(voter, area, false));
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
