package com.threess.summership.treasurehunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.fragment.LoginRegistrationFragment;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "main_activity";
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Init Fragment manger:
        fragmentManager = getSupportFragmentManager();
        MainActivity.addFragment(new LoginFragment(),LoginRegistrationFragment.TAG);

    }


    /**
     * This method calls the presenters method which replaces the fragment on top of the stack.
     * @param fragment new fragment
     * @param fragment_tag string tag
     */
    public static void replaceFragment(Fragment fragment, String fragment_tag){
        Fragment topFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if( topFragment == null ){
            // if there is nothing to replace, then add a new one:
            addFragment(fragment, fragment_tag);
        }else{
            // if there is fragment to replace, then replace it:
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragment_tag);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * This method returns the current fragment.
     * @return current fragment.
     */
    public static Fragment getCurrentFragment(){
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }

    /**
     * This method calls the presenters method which adds a new fragment on top of the stack.
     * @param fragment new fragment
     * @param fragment_tag string tag
     */
    private static void addFragment(Fragment fragment, String fragment_tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, fragment_tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Removes the fragment from fragment stack.
     * @param fragment fragment which should be removed
     */
    private void removeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }


}
