package com.threess.summership.treasurehunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    private static MainActivity sInstance;
    public static String TAG = "main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sInstance = this;

        // Init. FragmentNavigation:
        FragmentNavigation.getInstance( );

        // Show start page:
        FragmentNavigation.getInstance().addFragment(new LoginFragment());
    }

    public static MainActivity getContext (){
        return sInstance;
    }
}
