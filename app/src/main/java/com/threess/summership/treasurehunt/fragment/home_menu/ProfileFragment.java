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
import android.util.Log;
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
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.ChooserDialog;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.threess.summership.treasurehunt.util.Constant.Common.REQUEST_IMAGE_CAPTURE;

public class ProfileFragment extends Fragment {
    private static String TAG = ProfileFragment.class.getSimpleName();

    public static ProfileFragment sInstance;


    private ImageView profileImageView;
    private TextView mUserNameTextView;
    private TextView mTreasuresDiscoveredTextView;
    private TextView mTreasuresHiddenTextView;
    private TextView profileScoreTextView;
    private ImageView profileStarImageView;
    private TextView profileTreasureshiddenTextView;
    private TextView profileTreasuresdiscoveredTextView;
    private Button profileUpdateImageButton;
    private TextView profileUsernameImageView;
    private Button profileHomeButton;
    public static SavedData mDataManager;
    private static String mUserName;
    private static User mCurrentUser;
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
        sInstance = this;
        bindViews(view);
        mDataManager = new SavedData(getContext());
        setUserData();
        mUserName = mDataManager.readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY);
        loadProfileImage(mDataManager.getProfileImage());
        loadUserData();
        calculateUserTreasureStatus();
    }


    private void bindViews(View view) {
        profileImageView = view.findViewById(R.id.profile_image_view);
        mUserNameTextView = view.findViewById(R.id.username_text);
        mTreasuresDiscoveredTextView = view.findViewById(R.id.treasures_discovered);
        mTreasuresHiddenTextView = view.findViewById(R.id.treasures_hidden);
        profileScoreTextView = view.findViewById(R.id.score);
        mLogoutButton = view.findViewById(R.id.logout_button);
        mUpdateButton = view.findViewById(R.id.update);
        profileImageView.setOnClickListener(v -> profileImagePressed());
        mLogoutButton.setOnClickListener(v -> logOutButtonPressed());
        mUpdateButton.setOnClickListener(v -> loadUserData());
    }


    private void setUserData() {
        if (mUserName != null) {
            mUserNameTextView.setText(String.format(getResources().getString(R.string.profile_username), mUserName));
        }
    }


    private void loadUserData() {
        setUITreasuresHidden(mDataManager.readUserCreateTreasureNumber());
        setUITreasuresDiscovered(mDataManager.readUserClaimedTreasureNumber());
        ApiController.getInstance().getUser(mUserName, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mCurrentUser = response.body();
                if (mCurrentUser != null) {
                    setUIUserName(mCurrentUser.getUsername());
                    setUIScore(mCurrentUser.getScore());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Util.makeSnackbar(getView(), R.string.user_profile_no_informations_error_message, Snackbar.LENGTH_SHORT, Color.RED);
            }
        });
    }

    private void calculateUserTreasureStatus() {
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                if (response.body() != null) {
                    int createdTreasures = 0;
                    int discoveredTreasures = 0;
                    for (Treasure it : response.body()) {
                        if (it.getClaimedBy() != null && it.getClaimedBy().equals(mDataManager.getUserName())) {
                            discoveredTreasures++;
                        }
                        if (it.getUsername() != null && it.getUsername().equals(mDataManager.getUserName())) {
                            createdTreasures++;
                        }
                    }
                    mDataManager.saveUserClaimedTreasureNumber(discoveredTreasures);
                    mDataManager.saveUserCreateTreasureNumber(createdTreasures);
                    setUITreasuresHidden(createdTreasures);
                    setUITreasuresDiscovered(discoveredTreasures);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {
                setUITreasuresHidden(0);
                setUITreasuresDiscovered(0);
            }
        });
    }

    private void profileImagePressed(){
        new ChooserDialog().show(getChildFragmentManager(), TAG);
    }


    private void logOutButtonPressed() {
        SavedData dataManager = new SavedData(getContext());
        dataManager.setAutoLoginSwitch(false);
        mDataManager.clearProfileImage();
        dataManager.writeStringData("", Constant.SavedData.USER_PASSWORD_KEY);
        FragmentNavigation.getInstance(getContext()).showLoginFragment();
    }


    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, Constant.Common.GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.Common.GALLERY_REQUEST_CODE) {
            mDataManager.saveProfileImage(data.getData());
            loadProfileImage(data.getData());
            uploadImageToServer(data.getData());
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            ProfileFragment.mDataManager.saveProfileImage(data.getStringExtra(Constant.Prodile.FILE));
            ProfileFragment.sInstance.loadProfileImage(data.getStringExtra(Constant.Prodile.FILE));
            //updateUserProfileField();
            ProfileFragment.sInstance.uploadImageToServer(data.getStringExtra(Constant.Prodile.FILE));
        }

    }


    public void loadProfileImage(Uri imageUri) {
        if (imageUri != null) {
            Glide.with(getContext())
                    .load(imageUri)
                    .centerCrop()
                    .into(profileImageView);
        }
    }

    public void loadProfileImage(String image) {
        if (image != null) {
            Glide.with(getContext())
                    .load(image)
                    .centerCrop()
                    .into(profileImageView);
        }
    }

    public void uploadImageToServer(Uri fileUri) {
        String filePath = Util.getRealPathFromURIPath(fileUri, (Activity) getContext() );
        uploadImageToServer_(filePath);
    }

    public void uploadImageToServer(String fileStr) {
        uploadImageToServer_(fileStr);
    }

    private void uploadImageToServer_(String fileStr) {
        // Create a file object using file path

        File file = new File(fileStr);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        // Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        ApiController.getInstance().uploadProfileImage(part, description, mUserName, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Profile image upload failed!", t);
            }
        });
    }


    private void setUIUserName(String userName) {
        mUserNameTextView.setText(getString(R.string.profile_user_name_format, userName));
    }


    private void setUIScore(int score){
        profileScoreTextView.setText(score+"");
    }


    private void setUITreasuresHidden(int treasuresHidden) {
        mTreasuresHiddenTextView.setText(getString(R.string.profile_treasures_discovered_format, treasuresHidden));
    }


    private void setUITreasuresDiscovered(int treasuresDiscovered) {
        mTreasuresDiscoveredTextView.setText(getString(R.string.profile_treasures_hidden_format, treasuresDiscovered));
    }


}