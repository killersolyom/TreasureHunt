package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;


public class HomeFragment extends Fragment {

    public static String TAG = "home_fragment";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recent:
                    return true;
                case R.id.navigation_favoites:
                    loadFavoriteTreasureList();
                    return true;
                case R.id.navigation_map:
                    loadTreasureMap();
                    return true;
            }
            return false;
        }
    };

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navView = view.findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().getItem(1).setChecked(true);
        loadFavoriteTreasureList();
    }

    private void loadTreasureMap(){
        FragmentNavigation.getInstance( getContext() ).showMapViewFragmentInHomeFragment();
        // Fragment mapViewFragment = new MapViewFragment();
        // FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // transaction.replace(R.id.home_treasures_fragment_container, mapViewFragment).commit();
    }

    private void loadFavoriteTreasureList(){
        FavoriteTreasureFragment favoriteTreasureFragment = new FavoriteTreasureFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.home_treasures_fragment_container, favoriteTreasureFragment).commit();
    }
}
