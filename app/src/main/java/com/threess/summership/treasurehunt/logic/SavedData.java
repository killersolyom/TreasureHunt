package com.threess.summership.treasurehunt.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class SavedData {

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private static final String PROFILE_IMAGE_KEY = "profile_image_key";
    public static final String PROFILE_NAME_KEY = "profile_name_key";

    public SavedData(Context context) {
        try {
            preference = context.getSharedPreferences("TreasureHunt", 0);
            editor = preference.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeStringData(String value, String key) {
        editor.putString(key, value);
        editor.commit();
    }

    public String readStringData(String key) {
        return preference.getString(key, "");
    }

    public void writeBooleanData(Boolean value, String key) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean readBooleanData(String key) {
        return preference.getBoolean(key, false);
    }

    public Uri getProfileImage() {
        Uri imageUri = null;
        try {
            imageUri = Uri.parse(preference.getString(PROFILE_IMAGE_KEY, ""));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return imageUri;
    }

    public void saveProfileImage(Uri imageUri) {
        SharedPreferences.Editor myPrefsEdit = preference.edit();
        myPrefsEdit.putString(PROFILE_IMAGE_KEY, imageUri.toString());
        myPrefsEdit.apply();
    }
}
