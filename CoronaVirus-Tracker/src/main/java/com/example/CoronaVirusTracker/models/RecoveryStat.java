package com.example.CoronaVirusTracker.models;

public class RecoveryStat {

    private String State;
    private String country;
    private int latestRecoveryCases;
    private int diffFromPrevDayRecovery;



    public int getLatestRecoveryCases() {
        return latestRecoveryCases;
    }

    public void setLatestRecoveryCases(int latestRecoveryCases) {
        this.latestRecoveryCases = latestRecoveryCases;
    }

    public int getDiffFromPrevDayRecovery() {
        return diffFromPrevDayRecovery;
    }

    public void setDiffFromPrevDayRecovery(int diffFromPrevDayRecovery) {
        this.diffFromPrevDayRecovery = diffFromPrevDayRecovery;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "RecoveryStat{" +
                "State='" + State + '\'' +
                ", country='" + country + '\'' +
                ", latestRecoveryCases=" + latestRecoveryCases +
                ", diffFromPrevDayRecovery=" + diffFromPrevDayRecovery +
                '}';
    }
}

