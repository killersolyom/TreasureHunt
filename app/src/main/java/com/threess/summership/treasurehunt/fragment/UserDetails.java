package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.R;

public class UserDetails extends Fragment {
    private static final String TAG = UserDetails.class.getSimpleName();

    private final static String USERNAMEKEY="username";
    private final static String SCOREKEY="scorekey";
    private final static String IMAGEKEY="imagekey";

    private String imageUrl;
    private Button back;
    private TextView name,userscore;
    private ImageView image;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_userdetails, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageUrl = getArguments().getString(IMAGEKEY);
        name = view.findViewById(R.id.name);
        userscore = view.findViewById(R.id.userscore);
        image =view.findViewById(R.id.profile_image);

        name.setText(getString(R.string.Username) +": " + getArguments().getString(USERNAMEKEY));
        userscore.setText(getString(R.string.score)+ ": " + getArguments().getInt(SCOREKEY));
        Glide.with(view.getContext()).load(imageUrl).placeholder(getContext().getDrawable(R.drawable.default_pic)).into(image);

        back = view.findViewById(R.id.back);
        back.setOnClickListener(view1 -> getFragmentManager().popBackStack());
    }

    public static UserDetails newInstance(String username, int score, String imageUrl){
        UserDetails userDetails=new UserDetails();
        Bundle bundle=new Bundle();
        bundle.putString(USERNAMEKEY,username);
        bundle.putInt(SCOREKEY,score);
        bundle.putString(IMAGEKEY,imageUrl);
        userDetails.setArguments(bundle);
        return userDetails;
    }
}
