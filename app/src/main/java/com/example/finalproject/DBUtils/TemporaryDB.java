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
import com.example.finalproject.Entities.Vote;
import com.example.finalproject.Entities.Voter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemporaryDB {
    private static Map<String, Voter> voters = new HashMap<>();
    private static Map<String, Party> parties = new HashMap<>();
    private static Map<String, Admin> admins = new HashMap<>();
    private static Map<String, Vote> votes = new HashMap<>();
    private static Map<String, Area> areas = new HashMap<>();

    public static int oldestAge;
    public static int startVotingAge;
    public static Date startDesiredDate;
    public static Date endDesiredDate;

    public static void addVoter(Voter voter){
        voters.put(voter.getVoterId(),voter);
    }

    public static Map<String, Voter> getAllVoters(){
        return voters;
    }
    public static Map<String, Party> getAllParties(){
        return parties;
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
        dbUtils.getAllAdminsAndThereInheritors(Constant.DataBaseName, Constant.AdminsCollection, (AdminsCallback) (result, error)-> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        admins = new HashMap<>();
                        List<Admin> adminsTempList = (List<Admin>) result;
                        for (Admin a : adminsTempList){
                            admins.put(a.getVoterId(), a);
                        }
                    }
                }
        );
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

    public static int getOldestAge() {
        return oldestAge;
    }

    public static int startVotingAge() {
        return startVotingAge;
    }

//    public static void setOldestAge(){
//        TemporaryDB.getValueByKey("oldestAge");
//    }
//    public static void setStartVotingAge(){
//        TemporaryDB.getValueByKey("startAge");
//    }


    public static void dateOfEndVotingBeforeFormat() {
        dbUtils.getValueByKeyDateObject(Constant.DataBaseName, Constant.SystemParamsCollection, "endVoteDate", ((result, error) -> {
        if( result != null){
        com.example.finalproject.Entities.Date endDate = (com.example.finalproject.Entities.Date) result;
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Set the desired date
            calendar.set(Calendar.YEAR, endDate.getYear());
            calendar.set(Calendar.MONTH, endDate.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, endDate.getDay());
            calendar.set(Calendar.HOUR_OF_DAY, endDate.getHour());
            calendar.set(Calendar.MINUTE, endDate.getMinute());
            calendar.set(Calendar.SECOND, endDate.getSecund());

        // Get the date object from the modified calendar
        Date desiredDate = calendar.getTime();

        endDesiredDate = desiredDate;
            }
        }));
    }
    public static String dateOfEndVotingAfterFormat() {
        Date desiredDate = endDesiredDate;

        // Format the desired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(desiredDate);

        return formattedDate;
    }


    public static void dateOfStartVotingBeforeFormat() {
        dbUtils.getValueByKeyDateObject(Constant.DataBaseName, Constant.SystemParamsCollection, "startVoteDate", ((result, error) -> {
        if( result != null){

        com.example.finalproject.Entities.Date startDate = (com.example.finalproject.Entities.Date) result;
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Set the desired date
        calendar.set(Calendar.YEAR, startDate.getYear());
        calendar.set(Calendar.MONTH, startDate.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, startDate.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, startDate.getHour());
        calendar.set(Calendar.MINUTE, startDate.getMinute());
        calendar.set(Calendar.SECOND, startDate.getSecund());

        // Get the date object from the modified calendar
        Date desiredDate = calendar.getTime();

        startDesiredDate = desiredDate;
            }
        }));
    }
    public static String dateOfStartVotingAfterFormat() {
        Date desiredDate = startDesiredDate;

        // Format the desired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(desiredDate);

        return formattedDate;
    }

    public static void setOldestAge(String key) {
        dbUtils.getValueByKey(Constant.DataBaseName, Constant.SystemParamsCollection, key,(success, error) -> {
            if(success != null){
                oldestAge = (int) Math.round((double) success);
            }
        });
    }

    public static void setStartVotingAge(String key) {
        dbUtils.getValueByKey(Constant.DataBaseName, Constant.SystemParamsCollection, key,(success, error) -> {
            if(success != null){
                startVotingAge = (int) Math.round((double) success);
            }
        });
    }
}
