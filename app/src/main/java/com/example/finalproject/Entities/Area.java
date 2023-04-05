package com.example.finalproject.Entities;

import com.example.finalproject.Calculations.Generators;

public class Area {
    String id;
    String areaName;

    public Area(String areaName) {
        this.areaName = areaName;
        id = Generators.generateId();
    }

    public String getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }
}
