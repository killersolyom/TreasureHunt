package com.threess.summership.treasurehunt.fragment.home_menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.TreasureAdapter;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.LocatingUserLocation;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteTreasureFragment extends Fragment {
    public static final String TAG = FavoriteTreasureFragment.class.getSimpleName();

    private RecyclerView recycle;
    private TreasureAdapter adapter;
    private GeofencingClient geofencingClient;

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

        getAllActiveTreasures();

        FragmentNavigation.getInstance(getContext()).setAct(getActivity());
        geofencingClient = LocationServices.getGeofencingClient(getContext());


    }

    private void getAllActiveTreasures(){
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                adapter.refreshTreasure(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {

                
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter.getSelectedTreasure() != null){
            LatLng currentPosition = LocatingUserLocation.getInstance()
                    .tryToGetLocation(getContext());
            LatLng treasurePosition = new LatLng( adapter.getSelectedTreasure().getLocation_lat(),
                    adapter.getSelectedTreasure().getLocation_lon());
            if(currentPosition!=null){
                if(Util.distanceBetweenLatLngInMeter(currentPosition,treasurePosition) <= 10){
                    FragmentNavigation.getInstance(getContext()).showClaimTreasureFragment(new SavedData(getContext()).readStringData(SavedData.USER_PROFILE_NAME_KEY), adapter.getSelectedTreasure().getUsername());
                }
            }
        }
    }
}

