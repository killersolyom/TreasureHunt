package com.threess.summership.treasurehunt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Treasure implements Serializable {
    private static final String TAG = Treasure.class.getSimpleName();

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("passcode")
    @Expose
    private String passcode;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photo_clue")
    @Expose
    private String photo_clue;
    @SerializedName("location_lat")
    @Expose
    private double location_lat;
    @SerializedName("location_lon")
    @Expose
    private double location_lon;
    @SerializedName("prize_points")
    @Expose
    private double prize_points;
    @SerializedName("claimed")
    @Expose
    private boolean claimed;
    @SerializedName("claimed_by")
    @Expose
    private String claimed_by;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
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

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoClue() {
        return photo_clue;
    }

    public void setPhotoClue(String photo_clue) {
        this.photo_clue = photo_clue;
    }

    public double getLocationLat() {
        return location_lat;
    }

    public void setLocationLat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocationLon() {
        return location_lon;
    }

    public void setLocationLon(double location_lon) {
        this.location_lon = location_lon;
    }

    public double getPrizePoints() {
        return prize_points;
    }

    public void setPrizePoints(double prize_points) {
        this.prize_points = prize_points;
    }


    public boolean isClaimed() {
        return claimed;
    }

    public String getClaimedBy() {
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