package com.threess.summership.treasurehunt.fragment.home_menu;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.fragment.HomeFragment;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    public static final String TAG = MapViewFragment.class.getSimpleName();
    public static final String KEY1="key1";
    public static final String KEY2="key2";

    private MapView mMapView;
    private ArrayList<Treasure> treasures = new ArrayList<>();
    private GoogleMap googleMap = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        getTreasures();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMap() {
        mMapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        changeFocus(googleMap);
        drawMarkers(googleMap);
        getCurrentLocation(googleMap);
        googleMap.setOnMapLongClickListener(this);
    }

    private void drawMarkers(GoogleMap googleMap) {
        for (Treasure it : treasures) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(it.getLocationLat(),
                    it.getLocationLon())).title(it.getDescription()))
                    .setIcon(BitmapDescriptorFactory
                            .fromBitmap(Util.getDrawableTreasureImage(getContext())));
        }
    }


    private void changeFocus(GoogleMap googleMap) {
        MarkerOptions marker = new MarkerOptions().position(new LatLng(46.544595, 24.561126));
        googleMap.moveCamera(changeFocus(marker));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }

    private CameraUpdate changeFocus(MarkerOptions position) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder().include(position.getPosition());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12);
        return CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
    }

    private void getCurrentLocation(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {

        }
        googleMap.setMyLocationEnabled(true);
    }


    private void getTreasures(){
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                treasures.clear();
                treasures.addAll(response.body());
                drawMap();
            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        googleMap.addMarker(new MarkerOptions().position(latLng).
                title("Your Treasure").icon(BitmapDescriptorFactory.
                fromBitmap(Util.getDrawableTreasureImage(getContext()))));
        HideTreasureFragment.setMapPickCoordinates(latLng.latitude, latLng.longitude);
        HomeFragment.showPage( Constant.HomeViewPager.HIDE_TREASURE_IDX );
    }

    public static HideTreasureFragment newInstance(double latitude ,double longitude){
        HideTreasureFragment hideTreasureFragment=new HideTreasureFragment();
        Bundle args=new Bundle();
        args.putDouble(KEY1,latitude);
        args.putDouble(KEY2,longitude);
        hideTreasureFragment.setArguments(args);

        return hideTreasureFragment;
    }


}
