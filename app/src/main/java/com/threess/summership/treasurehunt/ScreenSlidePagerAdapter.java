package com.threess.summership.treasurehunt;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

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