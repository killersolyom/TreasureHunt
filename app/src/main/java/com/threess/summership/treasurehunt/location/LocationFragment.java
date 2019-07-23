package com.threess.summership.treasurehunt.location;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {


    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }



    private void loadTreasureMap(){
        Fragment mapViewFragment = new MapViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.home_treasures_fragment_container, mapViewFragment).commit();
    }

}
