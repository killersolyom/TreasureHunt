package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;


public class LoginFragment extends Fragment {

    public static String TAG = "login_fragment";
    private EditText nameText, passwordText;
    private Switch rememberMeSwitch, autoLoginSwitch;
    private SavedData dataManager;

    public LoginFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataManager = new SavedData(getContext());
        nameText = view.findViewById(R.id.loginName);
        passwordText = view.findViewById(R.id.loginPassword);
        rememberMeSwitch = view.findViewById(R.id.remember);
        autoLoginSwitch = view.findViewById(R.id.autologin);
    }
}
