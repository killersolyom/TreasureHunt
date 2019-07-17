package com.threess.summership.treasurehunt.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;
import com.threess.summership.treasurehunt.R;

public class UserProfile {
    @SerializedName("username")
    private String username;
    @SerializedName("score")
    private int score;
    @SerializedName("profile_picture")
    private String profilePicture;


    public UserProfile(String username) {
        this.username = username;
        this.score = 0;
        this.profilePicture = "";
    }

    public UserProfile(String username, int score) {
        this.username = username;
        this.score = score;
        this.profilePicture = "";
    }

    public UserProfile(String username, String profilePicture) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.score = 0;
    }

    public UserProfile(String username, int score, String profilePicture) {
        this.username = username;
        this.score = score;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
