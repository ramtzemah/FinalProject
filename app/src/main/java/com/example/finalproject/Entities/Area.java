package com.example.finalproject.Entities;

import android.util.Log;
import androidx.annotation.NonNull;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Area {
    private String id;
    private String areaName;
    private Map<String, Integer> partiesVotes;

    public Area(String areaName, Set<String> partiesKeys) {
        this.areaName = areaName;
        ObjectId objectId = new ObjectId();
        this.id = objectId.toString();
        this.partiesVotes = new HashMap<>();
        Iterator itr = partiesKeys.iterator();
        while(itr.hasNext()){
            partiesVotes.put((String) itr.next(), 0);
        }
    }

    public Area(@NonNull Document document) {
        Log.d("ptttt", " " +document);
        this.id = document.getString("areaId");
        this.areaName = document.getString("name");
        this.partiesVotes = new HashMap<>();

        Document partiesData = document.get("partiesVotes", Document.class);
        for (String partyId : partiesData.keySet()) {
            int voteCount = partiesData.getInteger(partyId);
            this.partiesVotes.put(partyId, voteCount);
        }
    }

    public String getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Map<String, Integer> getPartiesVotes() {
        return partiesVotes;
    }

    public void setPartiesVotes(Map<String, Integer> partiesVotes) {
        this.partiesVotes = partiesVotes;
    }
}
