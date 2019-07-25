package com.threess.summership.treasurehunt.fragment.home_menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ProfileFragment extends Fragment {
    public static String TAG = ProfileFragment.class.getSimpleName();

    private final int GALLERY_REQUEST_CODE = 862;

    @BindView(R.id.profile_image_view)
    ImageView profileImageView;

    @BindView(R.id.username_text)
    TextView userNameField;

    @BindView(R.id.Treasures_discovered)
    TextView treasuresdiscovered;

    @BindView(R.id.Treasures_hidden)
    TextView treasures_HiddenField;



    private TextView profileScoreTextView;
    private ImageButton profileStarImageButton;
    private Button profileUpdateImageButton;
    private TextView profileUsernameImageView;
    private TextView profileTreasuresdiscoveredTextView;
    private TextView profileTreasureshiddenTextView;
    private Button profileHomeButton;


    private SavedData dataManager;

    public ProfileFragment() {
        // constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataManager = new SavedData(getContext());
        setUserData();

        loadProfileImage(dataManager.getProfileImage());
    }


    private void setUserData() {
        String userName = dataManager.readStringData(SavedData.USER_PROFILE_NAME_KEY);
        if (userName != null) {
            userNameField.setText(String.format(getResources().getString(R.string.profile_username), userName));
        }
    }


    @OnClick(R.id.logout_button)
    void onLogoutClick(View view) {
        SavedData dataManager = new SavedData(getContext());
        dataManager.setAutoLoginSwitch(false);
        //dataManager.setRememberMeSwitch(false);
        //dataManager.writeStringData( SavedData.PROFILE_NAME_KEY, "" );
        //dataManager.writeStringData( SavedData.USER_PASSWORD_KEY, "" );
        FragmentNavigation.getInstance(getContext()).showLoginFragment();
    }

    @OnClick(R.id.update)
    void onUpdatePhotoClick() {
        pickFromGallery();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
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