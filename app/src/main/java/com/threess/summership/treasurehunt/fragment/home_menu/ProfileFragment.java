package com.threess.summership.treasurehunt.fragment.home_menu;

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


import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;


public class ProfileFragment extends Fragment {

    public static String TAG = "profile_fragment";
    private ImageView profileImageView;
    private TextView profileScoreTextView;
    private ImageButton profileStarImageButton;
    private Button profileUpdateImageButton;
    private TextView profileUsernameImageView;
    private TextView profileTreasuresdiscoveredTextView;
    private TextView profileTreasureshiddenTextView;
    private Button profileHomeButton;
    private Button profileLogoutButton;


    public ProfileFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO
        bindViewElements(view);

    }


    private void bindViewElements(View view){
        profileImageView = view.findViewById(R.id.profile_image_view);
        profileScoreTextView = view.findViewById(R.id.score);
        profileStarImageButton = view.findViewById(R.id.star_button);
        profileUpdateImageButton = view.findViewById(R.id.update);
        profileUsernameImageView = view.findViewById(R.id.username);
        profileTreasuresdiscoveredTextView = view.findViewById(R.id.Treasures_discovered);
        profileTreasureshiddenTextView = view.findViewById(R.id.Treasures_hidden);
        profileHomeButton = view.findViewById(R.id.home);
        profileLogoutButton = view.findViewById(R.id.logout);

        /*DELETE_ME*/profileUpdateImageButton.setOnClickListener( v -> {
        /*DELETE_ME*/    FragmentNavigation.getInstance(getContext()).showHideTreasureFragment();
        /*DELETE_ME*/});
    }

}
