package com.threess.summership.treasurehunt.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.ClaimTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HideTreasureFragment;
import com.threess.summership.treasurehunt.fragment.HomeFragment;
import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.fragment.RegistrationFragment;
import com.threess.summership.treasurehunt.fragment.SplashScreenFragment;
import com.threess.summership.treasurehunt.fragment.UserDetails;
import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;
import com.threess.summership.treasurehunt.fragment.home_menu.TopListFragment;
import com.threess.summership.treasurehunt.model.Treasure;

public class FragmentNavigation extends Fragment {
    public static final String TAG = FragmentNavigation.class.getSimpleName();

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static boolean mDoubleBackToExitPressedOnce;
    private static Handler mHandler = new Handler();

    public static FragmentNavigation getInstance(Context context){

        if( sInstance == null ){
            sInstance = new FragmentNavigation();
            mFragmentManager =  ((MainActivity) context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void popBackstack() {
        mFragmentManager.popBackStack();
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

    public void showClaimTreasureFragment(Treasure treasure){
        addFragment(ClaimTreasureFragment.newInstance(treasure), R.id.fragment_container);
    }

    public void showHideTreasureFragment(){
        replaceFragment(new HideTreasureFragment(), R.id.fragment_container);
    }

    public void showMapViewFragmentInHomeFragment(){
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment) {
            replaceFragment(new MapViewFragment(), R.id.home_treasures_fragment_container);
        }
    }

    public void showFavoriteTreasureListFragmentInHomeFragment(){
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment) {
            replaceFragment(new FavoriteTreasureFragment(), R.id.home_treasures_fragment_container);
        }
    }

    public void showProfileFragmentInHomeFragment(){
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment) {
            replaceFragment(new ProfileFragment(), R.id.home_treasures_fragment_container);
        }
    }

    public void showTopListFragment(){
        replaceFragment(new TopListFragment(), R.id.fragment_container);
    }

    public void showUserDetails(String username,int score,String imageurl){
        replaceFragment(UserDetails.newInstance(username,score,imageurl), R.id.fragment_container);
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
        try{
            mFragmentTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
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
            try {
                mFragmentTransaction.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
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


    public Fragment getCurrentFragmentOnMainActivity(){
        return getCurrentFragment( R.id.fragment_container );
    }

    public Fragment getCurrentFragmentOnHomeFragment(){
        return getCurrentFragment( R.id.home_treasures_fragment_container );
    }


    public void startNavigationToDestination(Treasure treasure, Context context){
        if( getCurrentFragment(R.id.fragment_container) instanceof HomeFragment){
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                    treasure.getLocation_lat()+"," +
                    treasure.getLocation_lon()+"&mode=w");
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
    public void onBackPressed(MainActivity activity) {

        if (mDoubleBackToExitPressedOnce) {
            mDoubleBackToExitPressedOnce = false;
            backPressed(activity);
            return;
        }

        mDoubleBackToExitPressedOnce = true;
        Toast.makeText(activity, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(new Runnable() {
           @Override
           public void run() {
               doubleBackToExitPressedOnce = false;
           }
       }, 2000);

    }

    private void backPressed(MainActivity activity){

        activity.moveTaskToBack(true);

    }

}
