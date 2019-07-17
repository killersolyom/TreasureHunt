package com.threess.summership.treasurehunt.logic;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.fragment.LoginFragment;

public class CustomProgressBar extends AsyncTask<Void, Integer, Void> {

    private ProgressBar splashScreenProgressBar;

    @Override
    protected void onPreExecute() {
        splashScreenProgressBar.setVisibility(View.VISIBLE);
        splashScreenProgressBar.setProgress(0);
        splashScreenProgressBar.setVisibility(View.VISIBLE);
        splashScreenProgressBar.setProgress(0);
    }

    public void setProgressBar(ProgressBar progressBar){
        this.splashScreenProgressBar = progressBar;
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
        splashScreenProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        splashScreenProgressBar.setEnabled(true);
        MainActivity.replaceFragment(new LoginFragment(), LoginFragment.TAG);
    }
}
