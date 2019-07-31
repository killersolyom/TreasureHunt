package com.threess.summership.treasurehunt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Treasure implements Serializable {
    private static final String TAG = Treasure.class.getSimpleName();

    @SerializedName("username")
    @Expose
    private String mUserName;

    @SerializedName("passcode")
    @Expose
    private String mPassCode;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("photo_clue")
    @Expose
    private String mPphotoClue;

    @SerializedName("location_lat")
    @Expose
    private double mLocationLat;

    @SerializedName("location_lon")
    @Expose
    private double mLocationLon;

    @SerializedName("prize_points")
    @Expose
    private double mPrizePoints;

    @SerializedName("claimed")
    @Expose
    private boolean mClaimed;

    @SerializedName("claimed_by")
    @Expose
    private String mClaimedBy;

    @SerializedName("createdAt")
    @Expose
    private String mCreatedAt;

    @SerializedName("updatedAt")
    @Expose
    private String mUpdatedAt;

    public String getUsername() {
        return mUserName;
    }

    public void setUsername(String username) {
        this.mUserName = username;
    }

    public String getPasscode() {
        return mPassCode;
    }

    public void setPasscode(String passcode) {
        this.mPassCode = passcode;
    }

    public String getTitle() { return mTitle; }

    public void setTitle(String title) { this.mTitle = title; }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getPhotoClue() {
        return mPphotoClue;
    }

    public void setPhotoClue(String photo_clue) {
        this.mPphotoClue = photo_clue;
    }

    public double getLocationLat() {
        return mLocationLat;
    }

    public void setLocationLat(double location_lat) {
        this.mLocationLat = location_lat;
    }

    public double getLocationLon() {
        return mLocationLon;
    }

    public void setLocationLon(double location_lon) {
        this.mLocationLon = location_lon;
    }

    public double getPrizePoints() {
        return mPrizePoints;
    }

    public void setPrizePoints(double prize_points) {
        this.mPrizePoints = prize_points;
    }


    public boolean isClaimed() {
        return mClaimed;
    }

    public String getClaimedBy() {
        return mClaimedBy;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setClaimed(boolean claimed) {
        this.mClaimed = claimed;
    }

    public void setClaimed_by(String claimed_by) {
        this.mClaimedBy = claimed_by;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.mUpdatedAt = updatedAt;
    }

}