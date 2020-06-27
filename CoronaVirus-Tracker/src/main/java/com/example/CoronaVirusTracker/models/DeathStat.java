package com.example.CoronaVirusTracker.models;

public class DeathStat {

    private String state;
    private String country;
    private int LatestDeath;
    private int previousDayDeath;
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

        public int getLatestDeath() {
            return LatestDeath;
        }

        public void setLatestDeath(int latestDeath) {
            LatestDeath = latestDeath;
        }

        public int getPreviousDayDeath() {
            return previousDayDeath;
        }

        public void setPreviousDayDeath(int previousDayDeath) {
            this.previousDayDeath = previousDayDeath;
        }


    @Override
    public String toString() {
        return "DeathStat{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", LatestDeath=" + LatestDeath +
                ", previousDayDeath=" + previousDayDeath +
                '}';
    }


    }

