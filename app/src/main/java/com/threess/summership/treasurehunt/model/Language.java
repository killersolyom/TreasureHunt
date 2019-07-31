package com.threess.summership.treasurehunt.model;

public class Language {

    private String mKey;
    private String mName;
    private int mDrawableId;

    public Language( String key, String name, int flag){
        mKey = key;
        mName = name;
        mDrawableId = flag;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public void setDrawableId(int mDrawableId) {
        this.mDrawableId = mDrawableId;
    }
}
