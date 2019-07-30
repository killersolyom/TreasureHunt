package com.threess.summership.treasurehunt.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.threess.summership.treasurehunt.fragment.HideTreasureFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.TopListFragment;

import java.util.ArrayList;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = ScreenSlidePagerAdapter.class.getSimpleName();

    private static ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList.add(new ProfileFragment());
        mFragmentList.add(new FavoriteTreasureFragment());
        mFragmentList.add(new TopListFragment());
        mFragmentList.add(new MapViewFragment());
        mFragmentList.add(new HideTreasureFragment());
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}