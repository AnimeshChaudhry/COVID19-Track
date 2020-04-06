package com.example.Covid19Track.models;

public class LocationStatistics {

    private String state;
    private String country;
    private int latestCases;
    private int casesDiff;

    public int getCasesDiff() {
        return casesDiff;
    }

    public void setCasesDiff(int casesDiff) {
        this.casesDiff = casesDiff;
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

    public int getLatestCases() {
        return latestCases;
    }

    public void setLatestCases(int latestCases) {
        this.latestCases = latestCases;
    }

    @Override
    public String toString() {
        return "LocationStatistics{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestCases=" + latestCases +
                '}';
    }
}
