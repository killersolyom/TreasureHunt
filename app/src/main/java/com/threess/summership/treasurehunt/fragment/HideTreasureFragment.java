package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.util.LocatingUserLocation;
import com.threess.summership.treasurehunt.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HideTreasureFragment extends Fragment {
    ImageView photoarrow;
    private Button button;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;
    private EditText pointsEditText;
    private EditText passcodeEditText;
    private EditText photoEditText;
    private SavedData dataManager;

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
        button = view.findViewById(R.id.Button);
        titleEditText = view.findViewById(R.id.teasing_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        locationEditText = view.findViewById(R.id.location_edit_text);
        pointsEditText = view.findViewById(R.id.prize_edit_text);
        passcodeEditText = view.findViewById(R.id.passcode_edit_text);
        photoarrow = view.findViewById(R.id.photo_clue_image_view);
        photoEditText = view.findViewById(R.id.photo_edit_text);
        dataManager = new SavedData(getContext());

        photoarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo image click function
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                final Treasure treasure = new Treasure();
                if (titleEditText.getText().toString().isEmpty()) {
                    titleEditText.requestFocus();
                    titleEditText.setError(getString(R.string.hidetreasureerror));
                    return;
                }
                else
                {
                    treasure.setTitle(titleEditText.getText().toString().trim());
                }
                if (descriptionEditText.getText().toString().isEmpty()) {
                    descriptionEditText.requestFocus();
                    descriptionEditText.setError(getString(R.string.hidetreasureerror));
                    return;
                }
                else
                {
                    treasure.setDescription(descriptionEditText.getText().toString().trim());
                }
                String locationStr = locationEditText.getText().toString().trim();
                if( ! isValidCoordinates(locationStr) ){
                    locationEditText.requestFocus();
                    locationEditText.setError("Wrong coordinates format! Usse: [latitude,longitude]");
                }
                else
                {
                    LatLng latLng = stringFormatToCoordinates( locationStr );
                    treasure.setLocation_lat(latLng.latitude);
                    treasure.setLocation_lon(latLng.longitude);
                }
                if (pointsEditText.getText().toString().isEmpty()) {
                    pointsEditText.requestFocus();
                    pointsEditText.setError(getString(R.string.hidetreasureerror));
                    return;
                }
                else
                {
                    treasure.setPrize_points(Double.parseDouble(pointsEditText.getText().toString()));
                }
                if (passcodeEditText.getText().toString().isEmpty()) {
                    passcodeEditText.requestFocus();
                    passcodeEditText.setError(getString(R.string.hidetreasureerror));
                    return;
                }
                else
                {
                    treasure.setPasscode(passcodeEditText.getText().toString().trim());
                }
                if (photoEditText.getText().toString().isEmpty()) {
                    photoEditText.requestFocus();
                    photoEditText.setError(getString(R.string.hidetreasureerror));
                    return;
                }
                else
                    {
                    treasure.setPhoto_clue(photoEditText.getText().toString().trim());
                    }
                treasure.setUsername(dataManager.readStringData("UserName"));
                ApiController.getInstance().createTreasure(treasure, new Callback<Treasure>() {
                    public void onResponse(@NonNull Call<Treasure> call, @Nullable Response<Treasure> response) {
                        if (response.errorBody() == null){
                            Snackbar snackbar = Snackbar.make(getView(),R.string.successful,Snackbar.LENGTH_LONG);
                            snackbar.show();
                            getFragmentManager().popBackStack();
                        } else {
                            Snackbar snackbar = Snackbar.make(getView(),R.string.create_treasure,Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                            snackbar.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Treasure> call, Throwable t) {
                        Log.e(TAG,"Failure: ",t);
                    }
                });
            }
        });

        loadCurrentCoordinates();

    }

    private void loadCurrentCoordinates() {

        try {
            LatLng latLng = LocatingUserLocation.getInstance().getLocation(getContext());
            String formattedCoordinates = coordinatesToStringFormat(latLng.latitude, latLng.longitude);
            locationEditText.setText( formattedCoordinates );
        }catch (Exception e){
            Util.makeSnackbar(getView(),R.string.hide_treasure_gps_get_location_failed,Snackbar.LENGTH_SHORT,R.color.red);
        }

    }

    private boolean isValidCoordinates(String coordinateStr){
        String pattern = "\\[[\\.0-9]+,[\\.0-9]+\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(coordinateStr);
        if (m.find( )) {
            return true;
        }else {
            return false;
        }
    }

    private String coordinatesToStringFormat(double latitude, double longitude){
        return "[" + latitude + "," + longitude + "]";
    }

    private LatLng stringFormatToCoordinates(String coordinateStr){
        if( ! isValidCoordinates(coordinateStr) ){
            return null;
        }
        coordinateStr = coordinateStr
                .trim()
                .replace(" ","")
                .replace("[","")
                .replace("]","");

        String aux[] = coordinateStr.split(",");

        double latitude = Double.parseDouble(aux[0]);
        double longitude = Double.parseDouble(aux[1]);

        return new LatLng(latitude,longitude);
    }


}
