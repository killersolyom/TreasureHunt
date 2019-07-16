package com.threess.summership.treasurehunt.logic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SavedData {

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    public SavedData(Context context) {
        try {
            preference = context.getSharedPreferences("TreasureHunt", 0);
            editor = preference.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeStringData(String value, String key){
        editor.putString(key,value);
        editor.commit();
    }

    public String readStringData(String key) {
        return preference.getString(key, "");
    }

    public void writeBooleanData(Boolean value, String key){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public Boolean readBooleanData(String key) {
        return preference.getBoolean(key, false);
    }
}
