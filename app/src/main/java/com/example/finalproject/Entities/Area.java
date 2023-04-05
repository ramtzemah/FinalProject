package com.example.finalproject.Entities;

import com.example.finalproject.Calculations.Generators;

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
        this.id = Generators.generateId();
        this.partiesVotes = new HashMap<>();
        Iterator itr = partiesKeys.iterator();
        while(itr.hasNext()){
            partiesVotes.put((String) itr.next(), 0);
        }
    }

    public String getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }
}
