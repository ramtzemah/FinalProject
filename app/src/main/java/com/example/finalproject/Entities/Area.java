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

    public Area(String areaName) {
        this.areaName = areaName;
        ObjectId objectId = new ObjectId();
        this.id = objectId.toString();
    }

    public Area(@NonNull Document document) {
        this.id = document.getString("areaId");
        this.areaName = document.getString("name");
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

}
