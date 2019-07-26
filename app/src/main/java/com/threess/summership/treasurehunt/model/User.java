package com.threess.summership.treasurehunt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    public static final String TAG = User.class.getSimpleName();

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("score")
    private int score;
    @Expose
    @SerializedName("profile_picture")
    private String profilePicture;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProfilpicture() {
        return profilePicture;
    }

    public void setProfilpicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
