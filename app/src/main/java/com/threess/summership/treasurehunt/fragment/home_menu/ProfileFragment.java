package com.threess.summership.treasurehunt.fragment.home_menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends Fragment {

    public static String TAG = Fragment.class.getSimpleName();
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

        loadProfileImage(getProfileImage());
    }


    private void setUserData() {
        String userName = dataManager.readStringData("UserName");
        if (userName != null) {
            userNameField.setText(String.format(getResources().getString(R.string.profile_username), userName));

        }
    }


    @OnClick(R.id.logout_button)
    void onLogoutClick(View view) {

    }

    @OnClick(R.id.update)
    void onUpdatePhotoClick() {
        pickFromGallery();
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    saveProfileImage(data.getData());
                    loadProfileImage(data.getData());
                    break;
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

    private Uri getProfileImage() {
        SharedPreferences myPrefs = getContext().getSharedPreferences("tag", 0);
        Uri imageUri = null;
        try {
            imageUri = Uri.parse(myPrefs.getString("url", "defaultString"));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageUri;
    }

    private void saveProfileImage(Uri imageUri) {
        SharedPreferences myPrefs = getContext().getSharedPreferences("tag", 0);
        SharedPreferences.Editor myPrefsEdit = myPrefs.edit();
        myPrefsEdit.putString("url", imageUri.toString());
        myPrefsEdit.apply();
    }


}
