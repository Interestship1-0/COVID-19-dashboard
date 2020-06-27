package com.example.CoronaVirusTracker.models;

public class DataState {
    public String state;
    public int cases;
    public int death;
    public int recovery;
    public int prevcase;
    public int prevdeath;
    public int prevrecover;

    public int getPrevcase() {
        return prevcase;
    }

    public void setPrevcase(int prevcase) {
        this.prevcase = prevcase;
    }

    public int getPrevdeath() {
        return prevdeath;
    }

    @Override
    public String toString() {
        return "DataState{" +
                "state='" + state + '\'' +
                ", cases=" + cases +
                ", death=" + death +
                ", recovery=" + recovery +
                ", prevcase=" + prevcase +
                ", prevdeath=" + prevdeath +
                ", prevrecover=" + prevrecover +
                '}';
    }

    public void setPrevdeath(int prevdeath) {
        this.prevdeath = prevdeath;
    }

    public int getPrevrecover() {
        return prevrecover;
    }

    public void setPrevrecover(int prevrecover) {
        this.prevrecover = prevrecover;
    }

    public DataState() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }
}
