package com.threess.summership.treasurehunt.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.ClaimTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HideTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HomeFragment;
import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.fragment.RegistrationFragment;
import com.threess.summership.treasurehunt.fragment.SplashScreenFragment;
import com.threess.summership.treasurehunt.fragment.TopListFragment;
import com.threess.summership.treasurehunt.fragment.UserDetails;
import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.User;

public class FragmentNavigation extends Fragment {
    public static final String TAG = FragmentNavigation.class.getSimpleName();

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static boolean mDoubleBackToExitPressedOnce = false;
    private static Handler mHandler = new Handler();
    private Activity act;
    private static int mMainActivityFragmentContainer;
    //private static int mHomeFragmentContainer;
    private static int mHomeTreasereFragmentContainer;


    public static FragmentNavigation getInstance(Context context){

        if( sInstance == null ){
            mMainActivityFragmentContainer = R.id.fragment_container;
            //mHomeFragmentContainer =  R.id.home_viewpager;
            mHomeTreasereFragmentContainer = R.id.home_treasures_fragment_container;
            sInstance = new FragmentNavigation();
            mFragmentManager =  ((MainActivity) context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public void showHomeFragment(){
        replaceFragment(new HomeFragment(), mMainActivityFragmentContainer);
    }

    public void showSplashScreenFragment(){
        replaceFragment(new SplashScreenFragment(), mMainActivityFragmentContainer);
    }

    public void showLoginFragment(){
        replaceFragment(new LoginFragment(), mMainActivityFragmentContainer);
    }

    public void showRegisterFragment(){
        replaceFragment(new RegistrationFragment(), mMainActivityFragmentContainer);
    }

    public void showClaimTreasureFragment(String username, String treasureName){
        replaceFragment(ClaimTreasureFragment.newInstance(username,treasureName), mMainActivityFragmentContainer);
    }

    public void showHideTreasureFragment(){
        replaceFragment(new HideTreasureFragment(), mMainActivityFragmentContainer);
    }

    public void showMapViewFragmentInHomeFragment(){
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            replaceFragment(new MapViewFragment(), mHomeTreasereFragmentContainer);
        }
    }

    public void showFavoriteTreasureListFragmentInHomeFragment(){
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            replaceFragment(new FavoriteTreasureFragment(), mHomeTreasereFragmentContainer);
        }
    }

    public void showProfileFragmentInHomeFragment(){
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            replaceFragment(new ProfileFragment(), mHomeTreasereFragmentContainer);
        }
    }

    public void showTopListFragment(){
        replaceFragment(new TopListFragment(), mMainActivityFragmentContainer);
    }

    public void showUserDetails(String username,int score,String imageurl){
        replaceFragment(UserDetails.newInstance(username,score,imageurl), mMainActivityFragmentContainer);
    }

    private boolean doubleBackToExitPressedOnce = false;


    /**
     * This method adds a new fragment on top of the stack.
     * @param fragment new fragment
     */
    private void addFragment(Fragment fragment, int container){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        try {
            mFragmentTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        //mFragmentManager.executePendingTransactions();
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
            //mFragmentManager.executePendingTransactions();
        }
    }


    /**
     * This method returns the current fragment.
     * @return current fragment.
     */
    private Fragment getCurrentFragment(int container){
        return mFragmentManager.findFragmentById(container);
    }


    public void startNavigationToDestination(Treasure treasure, Context context){
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                    treasure.getLocation_lat()+"," +
                    treasure.getLocation_lon()+"&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            act.startActivityForResult(mapIntent, 10);
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("3ss",requestCode+"");

    }

    /**
     * This method handles the application's back button presses and navigates to the corresponding
     * pages.
     * @param activity activity instance
     */
    public void onBackPressed(MainActivity activity) {

        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HomeFragment) {
            // If Home page is open: double press exit:

            doublePressExit(activity);

        }else {

            // If Hide treasure page is open --> show home page
            if( getCurrentFragment(mMainActivityFragmentContainer) instanceof HideTreasureFragment) {
                showHomeFragment();
                return;
            }

        }

        // Other cases:
        activity.moveTaskToBack(true);
    }


    private void doublePressExit(MainActivity activity){

        if (mDoubleBackToExitPressedOnce) {
            mDoubleBackToExitPressedOnce = false;
            activity.moveTaskToBack(true);
            return;
        }

        mDoubleBackToExitPressedOnce = true;
        Toast.makeText(activity, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
