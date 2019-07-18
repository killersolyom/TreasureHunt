package com.threess.summership.treasurehunt.fragment.home_menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.Treasure;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    public static String TAG = "MapView_fragment";
    private ArrayList<Treasure> treasures = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        getTreasures();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void drawMap(){
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
        changeFocus(googleMap);
        drawMarkers(googleMap);
    }

    private void drawMarkers(GoogleMap googleMap){
        for(Treasure it: treasures){
            googleMap.addMarker(
                    new MarkerOptions().position(
                            new LatLng(it.getLocation_lat(),
                                    it.getLocation_lon())).title(it.getDescription()))
                    .setIcon(BitmapDescriptorFactory.fromBitmap(getDrawableImage()));
        }
    }

    private Bitmap getDrawableImage(){
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon),100,100,false);
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    private void changeFocus(GoogleMap googleMap){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(46.544595, 24.561126));
        googleMap.moveCamera( changeFocus(marker));
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
    }

    private CameraUpdate changeFocus(MarkerOptions position){
        LatLngBounds.Builder builder = new LatLngBounds.Builder().include(position.getPosition());
        LatLngBounds bounds = builder.build();
        return CameraUpdateFactory.newLatLngBounds(bounds, 0);
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

}
