package com.example.finalproject.Entities;

public class AgesBlocks {
    int fromAge;
    int untilAge;
    int blocksByYears;

    public AgesBlocks(int fromAge, int untilAge, int blocksByYears) {
        this.fromAge = fromAge;
        this.untilAge = untilAge;
        this.blocksByYears = blocksByYears;
    }

    public int getFromAge() {
        return fromAge;
    }

    public int getUntilAge() {
        return untilAge;
    }

    public int getBlocksByYears() {
        return blocksByYears;
    }
}
