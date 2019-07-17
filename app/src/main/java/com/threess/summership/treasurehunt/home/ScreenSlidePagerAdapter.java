package com.threess.summership.treasurehunt.home;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.threess.summership.treasurehunt.favourite.FavouriteFragment;
import com.threess.summership.treasurehunt.location.LocationFragment;
import com.threess.summership.treasurehunt.recent.RecentFragment;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new RecentFragment();
            }
            case 1: {
                return new FavouriteFragment();
            }
            default:
                return new LocationFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}