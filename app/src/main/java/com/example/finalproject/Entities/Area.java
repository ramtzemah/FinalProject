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
    private String defaultVoteStation;

    public Area(String areaName, String defaultVoteStation) {
        this.areaName = areaName;
        ObjectId objectId = new ObjectId();
        this.id = objectId.toString();
        this.defaultVoteStation = defaultVoteStation;
    }

    public Area(@NonNull Document document) {
        this.id = document.getString("areaId");
        this.areaName = document.getString("name");
        this.defaultVoteStation = document.getString("defaultVoteStation");
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

    public String getDefaultVoteStation() {
        return defaultVoteStation;
    }

    public void setDefaultVoteStation(String defaultVoteStation) {
        this.defaultVoteStation = defaultVoteStation;
    }
}
