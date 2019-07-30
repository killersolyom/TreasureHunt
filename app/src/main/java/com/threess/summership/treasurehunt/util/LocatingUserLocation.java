package com.threess.summership.treasurehunt.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.threess.summership.treasurehunt.R;

public class LocatingUserLocation {
    private static final String TAG = LocatingUserLocation.class.getSimpleName();

    private LocationManager locationManager;
    private static final LocatingUserLocation ourInstance = new LocatingUserLocation();
    public static LocatingUserLocation getInstance() {
        return ourInstance;
    }
    private LocatingUserLocation() {
    }

    public String tryToGetLocationString(Context context){
        LatLng currentPosition = tryToGetLocation(context);
        if (currentPosition!=null){
            return  currentPosition.latitude + " , " + currentPosition.longitude;
        }
        return "";
    }

    public LatLng tryToGetLocation(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager!=null){
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps(context);
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return getLocation(context);
            }
        }
        return null;
    }

    private LatLng getLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( ((Activity)context),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.Common.REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return new LatLng(location.getLatitude(), location.getLongitude());
            } else{
                return null;
            }
        }
        return null;
    }

    private void buildAlertMessageNoGps(final Context context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(String.valueOf(R.string.turn_on_your_gps))
                .setCancelable(false)
                .setPositiveButton(String.valueOf(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton(String.valueOf(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
