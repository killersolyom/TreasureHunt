package com.threess.summership.treasurehunt.fragment.home_menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.TreasureAdapter;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Animator;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteTreasureFragment extends Fragment {
    public static final String TAG = FavoriteTreasureFragment.class.getSimpleName();

    private RecyclerView recycle;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private boolean mShowClaimTreasure;
    private static boolean mFirstStart = true;
    private Runnable runnable;
    private TreasureAdapter adapter;


    public FavoriteTreasureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_treasure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        playAnimations();
    }

    private void initComponents(View view){
        recycle = view.findViewById(R.id.recycler_view_contacts);
        adapter = new TreasureAdapter(this.getContext());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        initSwipeRefreshLayout(view);
        initRunnable();
        initLocationClient();
    }

    private void initSwipeRefreshLayout(View view){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_favorite_treasure);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getAllActiveTreasures();
            swipeRefreshLayout.postDelayed(runnable,Constant.FavoriteTreasure.STOP_SWIPE_REFRESHING_TIME);
        });
    }

    private void initRunnable(){
        runnable = () -> {
            if(getContext()!=null){
                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void initLocationClient(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback, null);
    }

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(TimeUnit.SECONDS.toMillis(1));


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locations = locationResult.getLocations();
            if (!locations.isEmpty()) {
                Location location = locations.get(0);
                if (adapter.getSelectedTreasure() != null) {
                    LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng treasurePosition = new LatLng(adapter.getSelectedTreasure().getLocationLat(),
                            adapter.getSelectedTreasure().getLocationLon());

                    if (Util.distanceBetweenLatLngInMeter(currentPosition, treasurePosition) <= 10 && adapter.getSelectedTreasure() != null) {
                        startActivity(new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

                        mShowClaimTreasure = true;
                    }

                }
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
        }
    };


    public  void  refreshTreasures(){
        getAllActiveTreasures();
    }

    private void getAllActiveTreasures() {
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                swipeRefreshLayout.setRefreshing(false);
                adapter.setTreasureList(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {


            }
        });
    }

    private void playAnimations() {
        if(mFirstStart) {
            mFirstStart = false;
            Animator recViewAnim = new Animator(getContext(), recycle, true);
            recViewAnim.AddSlide(0, 0, 1000, 0, 1800);
            recViewAnim.Start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            if(adapter.isEmpty()){
                getAllActiveTreasures();
            }else{
                adapter.notifyDataSetChanged();
            }
        }
        if (mShowClaimTreasure && adapter.getSelectedTreasure() != null) {
            Treasure treasure = adapter.getSelectedTreasure();
            adapter.clearSelectedTreasure();
            FragmentNavigation.getInstance(getContext()).showClaimTreasureFragment(treasure);
            mShowClaimTreasure = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

