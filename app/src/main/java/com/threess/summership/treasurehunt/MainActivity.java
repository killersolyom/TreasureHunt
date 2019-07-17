package com.threess.summership.treasurehunt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHomeFragment();
    }

    private void openHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_container, new HomeFragment())
                .commit();
    }

}
