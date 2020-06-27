package com.example.CoronaVirusTracker.models;

public class CountryWise {
    public String state;
    public String country;
    public int cases;
    public int deaths;
    public int recovered;

    public CountryWise() {
    }

    public CountryWise(String state, String country,int cases, int deaths, int recovered) {
        this.country=country;
        this.state = state;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    @Override
    public String toString() {
        return "CountryWise{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                '}';
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

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }



}
