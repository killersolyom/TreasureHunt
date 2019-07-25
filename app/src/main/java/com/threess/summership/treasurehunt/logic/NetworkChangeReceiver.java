package com.threess.summership.treasurehunt.logic;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.util.Util;

public class NetworkChangeReceiver extends BroadcastReceiver
{
    public static final String TAG = NetworkChangeReceiver.class.getSimpleName();
    Activity activity;

    public NetworkChangeReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try {
            //TODO change this
            if (isOnline(context)){
                Util.makeSnackbar(activity.findViewById(R.id.fragment_container),R.string.online_mode,Snackbar.LENGTH_LONG,R.color.blue300);
            }else {
                Util.makeSnackbar(activity.findViewById(R.id.fragment_container),R.string.offline_mode,Snackbar.LENGTH_LONG,R.color.orange700);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            return false;
        }
    }
}