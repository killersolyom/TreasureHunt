package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.ScreenSlidePagerAdapter;
import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.TopListFragment;


public class HomeFragment extends Fragment {

    public static String TAG = HomeFragment.class.getSimpleName();
    private ViewPager viewPager;
    public static BottomNavigationView bottomNavigationView;

    public HomeFragment() { }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.home_viewpager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        setupViewPager();
        setupBottomNavigation();

        viewPager.setCurrentItem(1);
    }
    @SuppressWarnings("deprecation")
    private void setupViewPager() {
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        adapter.addFragment( new ProfileFragment() );
        adapter.addFragment( new FavoriteTreasureFragment());
        adapter.addFragment( new TopListFragment() );
        adapter.addFragment( new MapViewFragment() );
        adapter.notifyDataSetChanged();
        viewPager.setAdapter( adapter );

        viewPager.setOnPageChangeListener(new PageChange());
    }

        private void setupBottomNavigation() {
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.action_profile: {
                                    viewPager.setCurrentItem(0);
                                    return true;
                                }

                                case R.id.action_recent: {
                                    viewPager.setCurrentItem(1);
                                    return true;
                                }

                                case R.id.action_favorites: {
                                    viewPager.setCurrentItem(2);
                                    return true;
                                }

                                case R.id.action_location: {
                                    viewPager.setCurrentItem(3);
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
    }

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottomNavigationView.setSelectedItemId(R.id.action_profile);
                    break;
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.action_recent);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.action_favorites);
                    break;
                case 3:
                    bottomNavigationView.setSelectedItemId(R.id.action_location);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
