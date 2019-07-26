package com.threess.summership.treasurehunt.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.threess.summership.treasurehunt.util.Constant;

public class SavedData {
    private static final String TAG = SavedData.class.getSimpleName();

    private SharedPreferences preference;

    public SavedData(Context context) {
        try {
            preference = context.getSharedPreferences(Constant.SavedData.SHARED_PREFERENCE_KEY, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeStringData(String value, String key) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readStringData(String key) {
        return preference.getString(key, "");
    }

    public void writeBooleanData(Boolean value, String key) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean readBooleanData(String key) {
        return preference.getBoolean(key, false);
    }

    public Uri getProfileImage() {
        Uri imageUri = null;
        try {
            imageUri = Uri.parse(preference.getString(Constant.SavedData.PROFILE_IMAGE_KEY, ""));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return imageUri;
    }

    public void saveProfileImage(Uri imageUri) {
        SharedPreferences.Editor myPrefsEdit = preference.edit();
        myPrefsEdit.putString(Constant.SavedData.PROFILE_IMAGE_KEY, imageUri.toString());
        myPrefsEdit.apply();
    }
}
