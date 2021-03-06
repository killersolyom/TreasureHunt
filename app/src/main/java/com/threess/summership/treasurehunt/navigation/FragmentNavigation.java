package com.threess.summership.treasurehunt.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.ClaimTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HomeFragment;
import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.fragment.RegistrationFragment;
import com.threess.summership.treasurehunt.fragment.SplashScreenFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.HideTreasureFragment;
import com.threess.summership.treasurehunt.fragment.MapViewFragment;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.util.Util;

public class FragmentNavigation extends Fragment {
    private static final String TAG = FragmentNavigation.class.getSimpleName();

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static boolean mDoubleBackToExitPressedOnce = false;
    private static Handler mHandler = new Handler();
    private Activity act;
    private static int mMainActivityFragmentContainer;
    //private static int mHomeFragmentContainer;
    //private static int mHomeTreasereFragmentContainer;

    public static FragmentNavigation getInstance(Context context) {

        if (sInstance == null) {
            mMainActivityFragmentContainer = R.id.fragment_container;
            sInstance = new FragmentNavigation();
            mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void popBackstack() {
        mFragmentManager.popBackStack();
    }

    public void showHomeFragment() {
        replaceFragment(new HomeFragment(), mMainActivityFragmentContainer);
    }

    public void showHomeFragmentWithPicker(double latitude, double longitude) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args=new Bundle();
        args.putBoolean("open_hide_treasure",true);
        args.putDouble(MapViewFragment.KEY1,latitude);
        args.putDouble(MapViewFragment.KEY2,longitude);
        homeFragment.setArguments(args);
        replaceFragment(homeFragment, mMainActivityFragmentContainer);

        //HomeFragment.showPage(Constant.HomeViewPager.HIDE_TREASURE_IDX);
        //HideTreasureFragment.setMapPickCoordinates(latitude,longitude);
    }

    public void showSplashScreenFragment() {
        replaceFragment(new SplashScreenFragment(), mMainActivityFragmentContainer);
    }

    public void showLoginFragment() {
        replaceFragment(new LoginFragment(), mMainActivityFragmentContainer);
    }

    public void showRegisterFragment() {
        replaceFragment(new RegistrationFragment(), mMainActivityFragmentContainer);
    }

    public void showClaimTreasureFragment(Treasure treasure) {
        addFragment(new ClaimTreasureFragment(treasure), mMainActivityFragmentContainer);
    }

    public void showMapViewFragmentInHomeFragment() {
        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            replaceFragment(new MapViewFragment(), mMainActivityFragmentContainer);
        }
    }


    /**
     * This method adds a new fragment on top of the stack.
     * @param fragment new fragment
     */
    private void addFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method removes the fragment from fragment stack.
     * @param fragment fragment which should be removed
     */
    private void removeFragment(Fragment fragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commit();
    }

    /**
     * This method replaces the fragment on top of the stack.
     * @param fragment new fragment
     */
    private void replaceFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(container);
        if (topFragment == null) {
            // if there is nothing to replace, then add a new one:
            addFragment(fragment, container);
        } else {
            // if there is fragment to replace, then replace it:
            mFragmentTransaction.replace(container, fragment, fragment.getTag());
            mFragmentTransaction.addToBackStack(null);
            try {
                mFragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //mFragmentManager.executePendingTransactions();
        }
    }


    /**
     * This method returns the current fragment.
     * @return current fragment.
     */
    private Fragment getCurrentFragment(int container) {
        return mFragmentManager.findFragmentById(container);
    }

    public void startNavigationToDestination(Treasure treasure, Context context) {
        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment && !treasure.isClaimed()) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                    treasure.getLocationLat() + "," +
                    treasure.getLocationLon() + "&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        }
    }


    /**
     * This method handles the application's back button presses and navigates to the corresponding
     * pages.
     * @param activity activity instance
     */
     public void onBackPressed(MainActivity activity, View view) {

        // If Home page is open: double press exit:
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            doublePressExit(activity, view);
            return;
        }

         if( (getCurrentFragment(mMainActivityFragmentContainer) instanceof RegistrationFragment)){
             popBackstack();
             return;
         }
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HideTreasureFragment
                || getCurrentFragment(mMainActivityFragmentContainer) instanceof ClaimTreasureFragment
                || (getCurrentFragment(mMainActivityFragmentContainer) instanceof MapViewFragment)) {
            showHomeFragment();
            return;
        }
        activity.moveTaskToBack(true);
    }



    private void doublePressExit(MainActivity activity, View view) {

        if (mDoubleBackToExitPressedOnce) {
            mDoubleBackToExitPressedOnce = false;
            activity.moveTaskToBack(true);
            return;
        }

        mDoubleBackToExitPressedOnce = true;

        Util.makeSnackbar(view, R.string.back_button_press, Snackbar.LENGTH_SHORT, R.color.blue300);

        mHandler.postDelayed(() -> mDoubleBackToExitPressedOnce = false, 2000);
    }


}
