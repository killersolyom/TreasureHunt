package com.threess.summership.treasurehunt.fragment.home_menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Constant;

import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private static String TAG = ProfileFragment.class.getSimpleName();

    ImageView profileImageView;
    TextView userNameField;
    TextView treasuresdiscovered;
    TextView treasures_HiddenField;
    TextView profileScoreTextView;
    ImageView profileStarImageButton;
    TextView profileTreasureshiddenTextView;
    TextView profileTreasuresdiscoveredTextView;
    private Button profileUpdateImageButton;
    private TextView profileUsernameImageView;
    private Button profileHomeButton;
    private SavedData dataManager;
    private String mUserName;
    private User mCurrentUser;
    private Button mLogoutButton;
    private Button mUpdateButton;

    public ProfileFragment() {
        // constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        dataManager = new SavedData(getContext());
        setUserData();

        mUserName = dataManager.readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY);

        loadProfileImage(dataManager.getProfileImage());
        loadUserData();
    }

    private void bindViews(View view){
        profileImageView = view.findViewById(R.id.profile_image_view);
        userNameField = view.findViewById(R.id.username_text);
        treasuresdiscovered = view.findViewById(R.id.treasures_discovered);
        treasures_HiddenField = view.findViewById(R.id.treasures_hidden);
        profileScoreTextView = view.findViewById(R.id.score);
        profileStarImageButton = view.findViewById(R.id.star_button);
        mLogoutButton = view.findViewById(R.id.logout_button);
        mUpdateButton = view.findViewById(R.id.update);
        mLogoutButton.setOnClickListener(v -> loginButtonPressed());
        mUpdateButton.setOnClickListener( v -> uprateButtonPressed());
    }


    private void setUserData() {
        if (mUserName != null) {
            userNameField.setText(String.format(getResources().getString(R.string.profile_username), mUserName));
        }
    }


    private void loadUserData() {
        ApiController.getInstance().getUser(mUserName, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mCurrentUser = response.body();
                loadCurrentUsersData();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Util.makeSnackbar(getView(), R.string.user_profile_no_informations_error_message,  Snackbar.LENGTH_SHORT, Color.RED);
            }
        });
    }

    private void loadCurrentUsersData() {

        // TODO: set score, hidden and discovered treasure count

        profileScoreTextView.setText("0");
    }


    private void loginButtonPressed(){
        SavedData dataManager = new SavedData(getContext());
        dataManager.setAutoLoginSwitch(false);
        //dataManager.setRememberMeSwitch(false);
        //dataManager.writeStringData( SavedData.PROFILE_NAME_KEY, "" );
        //dataManager.writeStringData( SavedData.USER_PASSWORD_KEY, "" );
        FragmentNavigation.getInstance(getContext()).showLoginFragment();
    }


    private void uprateButtonPressed(){
        pickFromGallery();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, Constant.Common.GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.Common.GALLERY_REQUEST_CODE) {
            dataManager.saveProfileImage(data.getData());
            loadProfileImage(data.getData());
        }
    }

    private void loadProfileImage(Uri imageUri) {
        if (imageUri != null) {
            Glide.with(getContext())
                    .load(imageUri)
                    .centerCrop()
                    .into(profileImageView);
        }
    }

}