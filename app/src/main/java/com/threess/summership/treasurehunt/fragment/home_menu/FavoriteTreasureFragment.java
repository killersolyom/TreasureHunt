package com.threess.summership.treasurehunt.fragment.home_menu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.TreasureAdapter;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteTreasureFragment extends Fragment {

    private static final String TAG = "3ss";
    private RecyclerView recycle;
    private TreasureAdapter adapter;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public FavoriteTreasureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_treasure, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycle = view.findViewById(R.id.recycler_view_contacts);
        adapter = new TreasureAdapter(this.getContext());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        getTreasures();
        FragmentNavigation.getInstance(getContext()).setAct(getActivity());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(TimeUnit.SECONDS.toMillis(1));


    private LocationCallback mLocationCallback =  new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locations = locationResult.getLocations();
            if (!locations.isEmpty()) {
                Location location = locations.get(0);
                if(adapter.getSelectedTreasure() != null){
                    LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
                    LatLng treasurePosition = new LatLng( adapter.getSelectedTreasure().getLocation_lat(),
                            adapter.getSelectedTreasure().getLocation_lon());
                    Log.e(TAG, "selected item not null : " + location.getAltitude() + ", long : " + location.getLongitude());
                        if(Util.distanceBetweenLatLngInMeter(currentPosition,treasurePosition) <= 10){
                            Log.e(TAG, "Claim Treasure : " + location.getAltitude() + ", long : " + location.getLongitude());
                            FragmentNavigation.getInstance(getContext()).showClaimTreasureFragment(new
                                            SavedData(getContext()).readStringData("UserName"),
                                    adapter.getSelectedTreasure().getUsername());
                        }else{
                            Log.e(TAG,"distance " + Util.distanceBetweenLatLngInMeter(currentPosition,treasurePosition));
                        }
                }
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private void getTreasures(){
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                adapter.addTreasure(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}

