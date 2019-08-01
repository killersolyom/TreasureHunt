package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.ScreenSlidePagerAdapter;
import com.threess.summership.treasurehunt.logic.SavedData;


public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Button toolbarButton;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setupViewPager();
        setupBottomNavigation();
        bindClickListeners();
    }

    private void bindClickListeners(){
        toolbarButton.setOnClickListener(v -> {
        });
    }
    private void bindViews(View view) {
        viewPager = view.findViewById(R.id.home_viewpager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        toolbar = view.findViewById(R.id.home_toolbar);
        toolbarButton = toolbar.findViewById(R.id.toolbar_settings);
    }

    @SuppressWarnings("deprecation")
    private void setupViewPager() {
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        viewPager.setOnPageChangeListener(new PageChange());
        viewPager.setCurrentItem(1);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
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
                        case R.id.action_hide_treasure: {
                            viewPager.setCurrentItem(4);
                            return true;
                        }
                    }
                    return false;
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
                    toolbar.setTitle("Profile - " + new SavedData(getContext()).getUserName());
                    toolbar.setTitleTextColor(getResources().getColor(R.color.gray900));
                    bottomNavigationView.setSelectedItemId(R.id.action_profile);
                    break;
                case 1:
                    toolbar.setTitle(R.string.treasures);
                    bottomNavigationView.setSelectedItemId(R.id.action_recent);
                    break;
                case 2:
                    toolbar.setTitle(R.string.top_list);
                    bottomNavigationView.setSelectedItemId(R.id.action_favorites);
                    break;
                case 3:
                    toolbar.setTitle(R.string.location);
                    bottomNavigationView.setSelectedItemId(R.id.action_location);
                    break;
                case 4:
                    toolbar.setTitle(R.string.hide_treasure);
                    bottomNavigationView.setSelectedItemId(R.id.action_hide_treasure);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
