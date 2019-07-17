package com.threess.summership.treasurehunt.home.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.home.ScreenSlidePagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static String TAG = HomeFragment.class.getSimpleName();
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.home_viewpager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        setupViewPager();
        setupBottomNavigation();
    }

    private void setupViewPager() {
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_recent: {
                                viewPager.setCurrentItem(0);
                                break;
                            }

                            case R.id.action_favorites: {
                                viewPager.setCurrentItem(1);
                                break;
                            }

                            case R.id.action_location: {
                                viewPager.setCurrentItem(2);
                                break;
                            }
                        }
                        return true;
                    }
                });
    }
}
