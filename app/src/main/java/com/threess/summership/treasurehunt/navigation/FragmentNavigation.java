package com.threess.summership.treasurehunt.navigation;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.ClaimTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HideTreasureFragment;
import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.fragment.RegistrationFragment;
import com.threess.summership.treasurehunt.fragment.SplashScreenFragment;
import com.threess.summership.treasurehunt.fragment.TopListFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;
import com.threess.summership.treasurehunt.home.fragment.HomeFragment;

public class FragmentNavigation extends Fragment{

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;



    public static FragmentNavigation getInstance(Context context){

        if( sInstance == null ){
            sInstance = new FragmentNavigation();
            mFragmentManager =  ((MainActivity) context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void showHomeFragment(){
        replaceFragment(new HomeFragment(), R.id.fragment_container);
    }

    public void showSplashScreenFragment(){
        replaceFragment(new SplashScreenFragment(), R.id.fragment_container);
    }

    public void showLoginFragment(){
        replaceFragment(new LoginFragment(), R.id.fragment_container);
    }

    public void showRegisterFragment(){
        replaceFragment(new RegistrationFragment(), R.id.fragment_container);
    }

    public void showClaimTreasureFragment(){
        replaceFragment(new ClaimTreasureFragment(), R.id.fragment_container);
    }

    public void showHideTreasureFragment(){
        replaceFragment(new HideTreasureFragment(), R.id.fragment_container);
    }

    public void showMapViewFragmentInHomeFragment(){
        // Show inside of the HomeFragment:
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment ) {
            replaceFragment(new MapViewFragment(), R.id.home_treasures_fragment_container);
        }
    }

    public void showProfileFragmentInHomeFragment(){
        // Show inside of the HomeFragment:
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment ) {
            replaceFragment(new ProfileFragment(), R.id.home_treasures_fragment_container);
        }
    }

    public void showTopListFragment(){
        replaceFragment(new TopListFragment(), R.id.fragment_container);
    }


    /**
     * This method adds a new fragment on top of the stack.
     * @param fragment new fragment
     */
    private void addFragment(Fragment fragment, int container){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    /**
     * This method removes the fragment from fragment stack.
     * @param fragment fragment which should be removed
     */
    private void removeFragment(Fragment fragment){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commit();
    }

    /**
     * This method replaces the fragment on top of the stack.
     * @param fragment new fragment
     */
    private void replaceFragment(Fragment fragment, int container){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(container);
        if( topFragment == null ){
            // if there is nothing to replace, then add a new one:
            addFragment(fragment, container);
        }else{
            // if there is fragment to replace, then replace it:
            mFragmentTransaction.replace(container, fragment, fragment.getTag());
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }

    /**
     * This method returns the current fragment.
     * @return current fragment.
     */
    private Fragment getCurrentFragment(int container){
        return mFragmentManager.findFragmentById(container);
    }

}
