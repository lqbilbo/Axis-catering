package com.company.team.bussiness.lottery.domain.entity;

public class Award {

    private int id;
    private int level;
    private String name;
    private int awardProbablity;
    private double cost;

    public int getAwardProbablity() {
        return awardProbablity;
    }

    public void setAwardProbablity(int awardProbablity) {
        this.awardProbablity = awardProbablity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
