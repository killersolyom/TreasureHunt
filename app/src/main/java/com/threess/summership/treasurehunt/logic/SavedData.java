package com.threess.summership.treasurehunt.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.threess.summership.treasurehunt.model.Language;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

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

    public void clearUserData(){
        setAutoLoginSwitch(false);
        clearProfileImage();
        writeStringData("", Constant.SavedData.USER_PASSWORD_KEY);
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

    public double readFloatData(String key) {return preference.getFloat(key, 0); }

    public  void writeFloatData(String key, float value){
        SharedPreferences.Editor editor= preference.edit();
        editor.putFloat(key, value);
        editor.apply();
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

    public void saveUserClaimedTreasureNumber(int number){
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(Constant.SavedData.USER_CLAIMED_TREASURE_NUMBER, number);
        editor.apply();
    }

    public void saveUserCreateTreasureNumber(int number){
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(Constant.SavedData.USER_CREATED_TREASURE_NUMBER, number);
        editor.apply();
    }

    public int readUserClaimedTreasureNumber(){
        return preference.getInt(Constant.SavedData.USER_CLAIMED_TREASURE_NUMBER,0);
    }

    public int readUserCreateTreasureNumber(){
        return preference.getInt(Constant.SavedData.USER_CREATED_TREASURE_NUMBER,0);
    }

    public void saveProfileImage(Uri imageUri) {
        SharedPreferences.Editor myPrefsEdit = preference.edit();
        myPrefsEdit.putString(Constant.SavedData.PROFILE_IMAGE_KEY, imageUri.toString());
        myPrefsEdit.apply();
    }

    private void clearProfileImage() {
        SharedPreferences.Editor myPrefsEdit = preference.edit();
        myPrefsEdit.putString(Constant.SavedData.PROFILE_IMAGE_KEY, "");
        myPrefsEdit.apply();
    }

    public void saveProfileImage(String imageStr) {
        SharedPreferences.Editor myPrefsEdit = preference.edit();
        myPrefsEdit.putString(Constant.SavedData.PROFILE_IMAGE_KEY, imageStr);
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
    public String getUserName(){
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
    public double getScore(){
        return  readFloatData(Constant.SavedData.USER_SCORE );
    }

    public void setScore(float score){
        writeFloatData(Constant.SavedData.USER_SCORE,score);
    }

    public void setLanguage(Language language){
        writeStringData(language.getKey(), Constant.SavedData.Language.LANGUAGE_KEY);
    }

    public Language getLanguage(Context context){
        String langStr = readStringData(Constant.SavedData.Language.LANGUAGE_KEY);
        if( langStr.isEmpty() ){
            return null; // error
        }
        return Util.getLanguageById(context, langStr);
    }
}