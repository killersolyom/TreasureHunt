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

    public void setAutoLoginSwitch(boolean bool) {
        writeBooleanData(Constant.SavedData.AUTO_LOGIN_SWITCH_KEY, bool);
    }

    public void setRememberMeSwitch(boolean bool) {
        writeBooleanData(Constant.SavedData.REMEMBER_ME_SWITCH_KEY, bool);
    }

    public boolean getAutoLoginSwitch() {
        return readBooleanData(Constant.SavedData.AUTO_LOGIN_SWITCH_KEY);
    }

    public boolean getRememberMeSwitch() {
        return readBooleanData(Constant.SavedData.REMEMBER_ME_SWITCH_KEY);
    }
    public String getCurrentUserName(){
        return readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY);
    }

    private void setPassword(String password){
        writeStringData(password,Constant.SavedData.USER_PASSWORD_KEY);
    }

    private void setUserName(String name){
        writeStringData(name,Constant.SavedData.USER_PROFILE_NAME_KEY);
    }

    public void setUserDataAfterRegistration(String userName,String password){
        setPassword(password);
        setUserName(userName);
        setRememberMeSwitch(true);
    }
}