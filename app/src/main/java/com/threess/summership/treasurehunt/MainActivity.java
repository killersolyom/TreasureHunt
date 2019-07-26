package com.threess.summership.treasurehunt;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.threess.summership.treasurehunt.logic.NetworkChangeReceiver;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BroadcastReceiver networkReceiver;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance(this).showSplashScreenFragment();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(getApplicationContext()!=null){
                    networkReceiver = new NetworkChangeReceiver(MainActivity.this);
                    registerNetworkBroadcastReceiver();
                }
            }
        };

        networkHandler();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constant.Common.PERMISSION_REQUEST_CODE);
        }
    }

    private void registerNetworkBroadcastReceiver() {
            registerReceiver(networkReceiver,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkBroadcastReceiver() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkBroadcastReceiver();
    }

    public void networkHandler(){
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }



    @Override
    public void onBackPressed() {
        FragmentNavigation.getInstance(getApplicationContext()).onBackPressed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int it: grantResults){
            if(it != PERMISSION_GRANTED){
                Util.makeSnackbar(findViewById(R.id.fragment_container),R.string.missing_permission,Snackbar.LENGTH_LONG,R.color.colorAccent);
                return;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
