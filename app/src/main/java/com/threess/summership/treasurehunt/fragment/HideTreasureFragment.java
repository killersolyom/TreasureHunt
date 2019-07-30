package com.threess.summership.treasurehunt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.camera.CameraActivity;
import com.threess.summership.treasurehunt.fragment.home_menu.MapViewFragment;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.LocatingUserLocation;
import com.threess.summership.treasurehunt.util.Util;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class HideTreasureFragment extends Fragment {
    private static final String TAG = HideTreasureFragment.class.getSimpleName();

    ImageView photoClueArrow;
    private Button button;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText pointsEditText;
    private EditText passcodeEditText;
    private EditText photoEditText;
    private EditText locationEditText;
    private SavedData dataManager;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private double latitude,longitude;
    private Treasure treasure;
    private File myIMGFile;

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
        findIds(view);
        if(getArguments() != null){
            latitude=getArguments().getDouble(MapViewFragment.KEY1);
            longitude=getArguments().getDouble(MapViewFragment.KEY2);
            locationEditText.setText( latitude +" , "+longitude);
        }else{
            locationEditText.setText(LocatingUserLocation.getInstance().tryToGetLocationString(getContext()));
        }
        titleEditText.setOnKeyListener((view18, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        });

        descriptionEditText.setOnKeyListener((view17, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        });

        photoEditText.setOnKeyListener((view16, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        });

        locationEditText.setOnKeyListener((view15, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        });

        pointsEditText.setOnKeyListener((view14, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        });

        passcodeEditText.setOnKeyListener((view13, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                buttonPress();
                return true;
            }
            return false;
        });


        photoClueArrow.setOnClickListener(view12 -> buttonCameraPress());

        button.setOnClickListener(view1 -> buttonPress());

        playAnimations(view);
        myIMGFile = null;

    }



    private void findIds(View view) {
        button = view.findViewById(R.id.Button);
        titleEditText = view.findViewById(R.id.teasing_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        pointsEditText = view.findViewById(R.id.prize_edit_text);
        passcodeEditText = view.findViewById(R.id.passcode_edit_text);
        photoClueArrow = view.findViewById(R.id.photo_clue_image_view);
        photoEditText = view.findViewById(R.id.photo_edit_text);
        locationEditText = view.findViewById(R.id.location_edit_text);
        dataManager = new SavedData(getContext());
    }

    private void buttonPress() {
        if (checkInputFields()) {
            Treasure treasure = getInputFields();
            uploadTreasure(treasure);
        }
    }

    private void buttonCameraPress(){
        Intent takePictureIntent = new Intent(getActivity(),CameraActivity.class);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        // Take image

        // Upload image (API)

        // get->set link

    }

    private boolean checkInputFields() {
        if (titleEditText.getText().toString().isEmpty()) {
            titleEditText.requestFocus();
            titleEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        if (descriptionEditText.getText().toString().isEmpty()) {
            descriptionEditText.requestFocus();
            descriptionEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        if (photoEditText.getText().toString().isEmpty()) {
            photoEditText.requestFocus();
            photoEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        if (locationEditText.getText().toString().isEmpty()) {
            locationEditText.requestFocus();
            locationEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        if (pointsEditText.getText().toString().isEmpty()) {
            pointsEditText.requestFocus();
            pointsEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        if (passcodeEditText.getText().toString().isEmpty()) {
            passcodeEditText.requestFocus();
            passcodeEditText.setError(getString(R.string.hidetreasureerror));
            return false;
        }
        return true;
    }

    private Treasure getInputFields() {
        treasure = new Treasure();
        treasure.setTitle(titleEditText.getText().toString().trim());
        treasure.setDescription(descriptionEditText.getText().toString().trim());
        treasure.setPrize_points(Double.parseDouble(pointsEditText.getText().toString()));
        treasure.setPasscode(passcodeEditText.getText().toString().trim());
        treasure.setPhoto_clue(photoEditText.getText().toString().trim());
        treasure.setUsername(dataManager.readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY));
        treasure.setLocation_lat(latitude);
        treasure.setLocation_lon(longitude);
        return treasure;
    }

    private void uploadTreasure(Treasure treasure) {
        ApiController.getInstance().createTreasure(treasure, new Callback<Treasure>() {
            public void onResponse(@NonNull Call<Treasure> call, @Nullable Response<Treasure> response) {
                if (response.errorBody() == null) {
                    if(!myIMGFile.getAbsolutePath().equals("")){
                        uploadToServer(myIMGFile.getAbsolutePath());
                        getFragmentManager().popBackStack();
                    }else{
                        uploadToServer("");
                        getFragmentManager().popBackStack();
                    }
                } else {
                    Util.errorHandling(getView(),response.errorBody().source().toString(),response.code());
                }
            }

            @Override
            public void onFailure(Call<Treasure> call, Throwable t) {
                Util.makeSnackbar(getView(), R.string.unreachable, Snackbar.LENGTH_LONG, R.color.orange700);
            }
        });
    }

    private void playAnimations(View view) {

        Context c = getContext();
        // TODO implement after finishing the .xml

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(getActivity().getString(R.string.file_string));
            myIMGFile = new File(filePath);
            LatLng latLng = LocatingUserLocation.getInstance().tryToGetLocation(getContext());
            this.latitude = latLng.latitude;
            this.longitude = latLng.longitude;
            locationEditText.setText(latLng.latitude+ "," + latLng.longitude);
            photoEditText.setText(filePath);
        }
    }


    private void uploadToServer(String filePath) {
        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        ApiController.getInstance().uploadTreasureImageClue(part, description, treasure.getUsername(), treasure.getPasscode(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "Sikertelen", t);
            }
        });
    }

}
