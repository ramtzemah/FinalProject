package com.example.finalproject.Calculations;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Enums.Gender;

import java.util.UUID;

public class Generators {

    public static String generateId(){
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);
        return uniqueID;
    }

    public static void addVotersToDB(){
        TemporaryDB.addVoter(new Voter("aaa","aaa",19, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("bbb","bbb",20, Gender.FEMALE,"Hedera",1,"0"));
        TemporaryDB.addVoter(new Voter("ccc","ccc",18, Gender.MALE,"Tel Aviv",1,"0"));
        TemporaryDB.addVoter(new Voter("ddd","ddd",21, Gender.FEMALE,"Hedera",1,"0"));
        TemporaryDB.addVoter(new Voter("eee","eee",27, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("fff","fff",28, Gender.FEMALE,"Tel Aviv",1,"0"));
        TemporaryDB.addVoter(new Voter("ggg","ggg",49, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("hhh","hhh",20, Gender.FEMALE,"Hedera",1,"0"));
        TemporaryDB.addVoter(new Voter("iii","iii",29, Gender.FEMALE,"Tel Aviv",1,"0"));
        TemporaryDB.addVoter(new Voter("jjj","jjj",57, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("kkk","kkk",35, Gender.FEMALE,"Hedera",1,"0"));
        TemporaryDB.addVoter(new Voter("lll","lll",43, Gender.MALE,"Tel Aviv",1,"0"));
        TemporaryDB.addVoter(new Voter("mmm","mmm",52, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("nnn","nnn",60, Gender.FEMALE,"Hedera",1,"0"));
        TemporaryDB.addVoter(new Voter("ooo","ooo",55, Gender.FEMALE,"Tel Aviv",1,"0"));
        TemporaryDB.addVoter(new Voter("ppp","ppp",39, Gender.MALE,"Eilat",1,"0"));
    }

    public static void addPartiesToDB(){
        TemporaryDB.addParty(new Party("Likud", "aaaa"));
        TemporaryDB.addParty(new Party("Ozma Yehodit", "bbbb"));
        TemporaryDB.addParty(new Party("Merez", "cccc"));
        TemporaryDB.addParty(new Party("Israel Biteno", "dddd"));
    }

    public static void addAdminToDB(){
        TemporaryDB.addAdminLeader(new Admin("qqq","qqq",39, Gender.MALE,"Eilat",1,"0","Eilat",true));
    }
}
