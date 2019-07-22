package com.threess.summership.treasurehunt;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.threess.summership.treasurehunt.logic.NetworkChangeReceiver;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "main_activity";
    private int permissionResultCode = 10;
    private BroadcastReceiver networkReceiver;
    private View v = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance( this ).showSplashScreenFragment();
        networkHandler();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    permissionResultCode);
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
        new CountDownTimer(2000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                networkReceiver = new NetworkChangeReceiver(MainActivity.this);
                registerNetworkBroadcastReceiver();
            }
        }.start();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int it: grantResults){
            if(it != PERMISSION_GRANTED){
                Snackbar snackbar = Snackbar.make(findViewById(R.id.fragment_container),R.string.missing_permission,Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                snackbar.show();
                return;
            }
        }

    }
}
