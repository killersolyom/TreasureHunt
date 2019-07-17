package com.threess.summership.treasurehunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FragmentNavigation.getInstance( this ).showSplashScreenFragment(); // Use this when Splash screen is ready.
        FragmentNavigation.getInstance( this ).showLoginFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
