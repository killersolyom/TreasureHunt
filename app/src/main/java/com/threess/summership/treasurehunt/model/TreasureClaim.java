package com.threess.summership.treasurehunt.model;

import com.google.gson.annotations.SerializedName;

public class TreasureClaim {
    private static final String TAG = TreasureClaim.class.getSimpleName();

    @SerializedName("username")
    private String username;
    @SerializedName("passcode")
    private String passcode;

    public  TreasureClaim(String username,String passcode){
        this.username=username;
        this.passcode=passcode;
    }


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
}
