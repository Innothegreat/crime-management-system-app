package com.example.crimemanagementapp;

import androidx.annotation.NonNull;

public class Crime {
    private String crimeType;
    private String dateTime;
    private String location;
    private String description;

    // Default constructor required for Firebase
    public Crime() {}

    public Crime(String crimeType, String dateTime, String location, String description) {
        this.crimeType = crimeType;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
    }

    // Getters and setters
    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @NonNull
    @Override
    public String toString() {
        return "Crime Type: " + crimeType + "\n"
                + "Date and Time: " + dateTime + "\n"
                + "Location: " + location + "\n"
                + "Description: " + description;
    }
}

