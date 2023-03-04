package com.example.finalproject.DBUtils;

import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;

import java.util.HashMap;
import java.util.Map;

public class TemporaryDB {
    private static Map<String, Voter> voters = new HashMap<>();
    private static Map<String, Party> parties = new HashMap<>();

    public static void addVoter(Voter voter){
        voters.put(voter.getVoterId(),voter);
    }

    public static Map<String, Voter> getAllVoters(){
        return voters;
    }

    public static void addParty(Party party){
        parties.put(party.getPartyId(),party);
    }

    public static Map<String, Party> getAllParties(){
        return parties;
    }

    public static int getAllVotersNumber(){
        return voters.size();
    }

    public static int getAllPartiesNumber(){
        return parties.size();
    }


}
