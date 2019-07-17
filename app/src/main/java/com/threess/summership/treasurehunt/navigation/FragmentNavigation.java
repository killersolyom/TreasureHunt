package com.threess.summership.treasurehunt.navigation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;

import java.util.concurrent.CopyOnWriteArrayList;

public class FragmentNavigation extends Fragment{

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static Activity activity;



    public static FragmentNavigation getInstance(){

        if( sInstance == null ){
            sInstance = new FragmentNavigation();
            mFragmentManager =  MainActivity.getContext().getSupportFragmentManager();
        }

        return sInstance;
    }


    /**
     * This method adds a new fragment on top of the stack.
     * @param fragment new fragment
     */
    public void addFragment(Fragment fragment){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    /**
     * This method removes the fragment from fragment stack.
     * @param fragment fragment which should be removed
     */
    public void removeFragment(Fragment fragment){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commit();
    }

    /**
     * This method replaces the fragment on top of the stack.
     * @param fragment new fragment
     */
    public void replaceFragment(Fragment fragment){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        if( topFragment == null ){
            // if there is nothing to replace, then add a new one:
            addFragment(fragment);
        }else{
            // if there is fragment to replace, then replace it:
            mFragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getTag());
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }

    /**
     * This method returns the current fragment.
     * @return current fragment.
     */
    public Fragment getCurrentFragment(){
        return mFragmentManager.findFragmentById(R.id.fragment_container);
    }

}
