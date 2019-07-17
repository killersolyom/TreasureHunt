package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SplashProgressTask;


public class SplashScreenFragment extends Fragment {

    public static String TAG = "splash_screen_fragment";
    private ProgressBar splashScreenProgressBar;
    private SplashProgressTask loadingProgress;

    public SplashScreenFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splashScreenProgressBar = view.findViewById(R.id.splashScreenProgressBar);
        splashScreenProgressBar.setEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        loadingProgress.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialization();
    }

    private void initialization() {
        loadingProgress = new SplashProgressTask();
        loadingProgress.setProgressData(splashScreenProgressBar,getContext());
        loadingProgress.execute();
    }

}
