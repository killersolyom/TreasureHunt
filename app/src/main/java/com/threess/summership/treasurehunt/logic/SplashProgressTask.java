package com.threess.summership.treasurehunt.logic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.fragment.LoginFragment;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

public class SplashProgressTask extends AsyncTask<Void, Integer, Void> {

    private ProgressBar splashScreenProgressBar;
    private Context context;

    @Override
    protected void onPreExecute() {
        splashScreenProgressBar.setVisibility(View.VISIBLE);
        splashScreenProgressBar.setProgress(0);
    }

    public void setProgressData(ProgressBar progressBar,Context context){
        this.splashScreenProgressBar = progressBar;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i=0; i<100; i+=2){
            publishProgress(i);
            SystemClock.sleep(45);
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        splashScreenProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        splashScreenProgressBar.setEnabled(true);
        FragmentNavigation.getInstance( context).showLoginFragment();
    }
}
