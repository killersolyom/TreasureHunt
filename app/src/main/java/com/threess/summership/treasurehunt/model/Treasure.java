package com.threess.summership.treasurehunt.model;

import com.google.gson.annotations.SerializedName;

public class Treasure {
    @SerializedName("username")
    private String username;
    @SerializedName("passcode")
    private String passcode;
    @SerializedName("description")
    private String description;
    @SerializedName("photo_clue")
    private String photo_clue;
    @SerializedName("location_lat")
    private double location_lat;
    @SerializedName("location_lon")
    private double location_lon;
    @SerializedName("prize_points")
    private double prize_points;
    @SerializedName("claimed")
    private boolean claimed;
    @SerializedName("claimed_by")
    private String claimed_by;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_clue() {
        return photo_clue;
    }

    public void setPhoto_clue(String photo_clue) {
        this.photo_clue = photo_clue;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_lon() {
        return location_lon;
    }

    public void setLocation_lon(double location_lon) {
        this.location_lon = location_lon;
    }

    public double getPrize_points() {
        return prize_points;
    }

    public void setPrize_points(double prize_points) {
        this.prize_points = prize_points;
    }


    public boolean isClaimed() {
        return claimed;
    }

    public String getClaimed_by() {
        return claimed_by;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public void setClaimed_by(String claimed_by) {
        this.claimed_by = claimed_by;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}