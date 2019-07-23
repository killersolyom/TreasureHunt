package com.threess.summership.treasurehunt.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;


public class HideTreasureFragment extends Fragment {

    public static String TAG = "hide_treasure_fragment";

    public HideTreasureFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hide_treasure, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO
        selectLocation();

    }

    private void selectLocation(){
        // Create a new Places client instance
//        PlacesClient placesClient = Places.createClient(getActivity());
//        placesClient.fetchPlace()
///*

/*        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity() ), MainActivity.PLACE_PICKER_CODE);

        } catch (Exception e){
            e.printStackTrace();
        }

    }*/
}
