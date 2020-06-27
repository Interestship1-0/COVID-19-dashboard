package com.example.CoronaVirusTracker.models;

public class MergeStat {
    private String state;
    private  String country;
    private int totalglobal;
    private int prevglobal;

    @Override
    public String toString() {
        return "MergeStat{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalglobal=" + totalglobal +
                ", prevglobal=" + prevglobal +
                ", totalDeath=" + totalDeath +
                ", prevdeath=" + prevdeath +
                ", totalRecover=" + totalRecover +
                ", prevrecover=" + prevrecover +
                '}';
    }

    private int totalDeath;
    private int prevdeath;
    private int totalRecover;
    private int prevrecover;

    public MergeStat() {

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalglobal() {
        return totalglobal;
    }

    public void setTotalglobal(int totalglobal) {
        this.totalglobal = totalglobal;
    }

    public int getPrevglobal() {
        return prevglobal;
    }

    public void setPrevglobal(int prevglobal) {
        this.prevglobal = prevglobal;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }

    public int getPrevdeath() {
        return prevdeath;
    }

    public void setPrevdeath(int prevdeath) {
        this.prevdeath = prevdeath;
    }

    public int getTotalRecover() {
        return totalRecover;
    }

    public void setTotalRecover(int totalRecover) {
        this.totalRecover = totalRecover;
    }

    public int getPrevrecover() {
        return prevrecover;
    }

    public void setPrevrecover(int prevrecover) {
        this.prevrecover = prevrecover;
    }
}
