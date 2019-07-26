package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HideTreasureFragment extends Fragment {
    public static final String TAG = HideTreasureFragment.class.getSimpleName();

    ImageView photoarrow;
    private Button button;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText pointsEditText;
    private EditText passcodeEditText;
    private EditText photoEditText;
    private EditText locationEditText;
    private SavedData dataManager;

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
        titleEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        descriptionEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        photoEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        locationEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        pointsEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        passcodeEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    buttonPress();
                    return true;
                }
                return false;
            }
        });


        photoarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo image click function
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                buttonPress();
            }
        });

    }

    private void findIds(View view) {
        button = view.findViewById(R.id.Button);
        titleEditText = view.findViewById(R.id.teasing_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        pointsEditText = view.findViewById(R.id.prize_edit_text);
        passcodeEditText = view.findViewById(R.id.passcode_edit_text);
        photoarrow = view.findViewById(R.id.photo_clue_image_view);
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
        final Treasure treasure = new Treasure();
        treasure.setTitle(titleEditText.getText().toString().trim());
        treasure.setDescription(descriptionEditText.getText().toString().trim());
        treasure.setPrize_points(Double.parseDouble(pointsEditText.getText().toString()));
        treasure.setPasscode(passcodeEditText.getText().toString().trim());
        treasure.setPhoto_clue(photoEditText.getText().toString().trim());
        treasure.setUsername(dataManager.readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY));
        return treasure;
    }

    private void uploadTreasure(Treasure treasure) {
        ApiController.getInstance().createTreasure(treasure, new Callback<Treasure>() {
            public void onResponse(@NonNull Call<Treasure> call, @Nullable Response<Treasure> response) {
                if (response.errorBody() == null) {
                    Snackbar snackbar = Snackbar.make(getView(), R.string.successful, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    getFragmentManager().popBackStack();
                } else {
                    Snackbar snackbar = Snackbar.make(getView(), R.string.create_treasure, Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Treasure> call, Throwable t) {
                Log.e(TAG, "Failure: ", t);
            }
        });
    }


}
