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

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public void setDrawableId(int drawableId) {
        this.mDrawableId = drawableId;
    }
}
