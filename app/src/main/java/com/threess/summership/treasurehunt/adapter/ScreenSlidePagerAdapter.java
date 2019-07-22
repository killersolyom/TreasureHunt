package com.threess.summership.treasurehunt.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        if (mFragmentList != null && fragment != null) {
            mFragmentList.add(fragment);
        }
    }

}