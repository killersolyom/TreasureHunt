package com.threess.summership.treasurehunt.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;

public class SavedData {
    public static final String TAG = SavedData.class.getSimpleName();

    private SharedPreferences preference;
    private static final String SHARED_PREFERENCE_KEY = "TreasureHunt";
    private static final String PROFILE_IMAGE_KEY = "profile_image_key";
    public static final String USER_PROFILE_NAME_KEY = "profile_name_key";
    public static final String USER_PASSWORD_KEY = "user_password_key";
    public static final String REMEMBER_ME_SWITCH_KEY = "RememberMeSwitch";
    public static final String AUTO_LOGIN_SWITCH_KEY = "AutoLoginSwitch";

    public SavedData(Context context) {
        try {
            preference = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
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

    public void writeBooleanData(String key, Boolean value) {
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

    public void setAutoLoginSwitch(boolean bool) {
        writeBooleanData(SavedData.AUTO_LOGIN_SWITCH_KEY, bool);
    }

    public void setRememberMeSwitch(boolean bool) {
        writeBooleanData(SavedData.REMEMBER_ME_SWITCH_KEY, bool);
    }

    public boolean getAutoLoginSwitch() {
        return readBooleanData(SavedData.AUTO_LOGIN_SWITCH_KEY);
    }

    public boolean getRememberMenSwitch() {
        return readBooleanData(SavedData.REMEMBER_ME_SWITCH_KEY);
    }
}