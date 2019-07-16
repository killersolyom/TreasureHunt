package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.threess.summership.treasurehunt.R;


public class RegistrationFragment extends Fragment {

    public static String TAG = "registration_fragment";
    private EditText username, password, confirm_password;
    private Button  register, cancel;

    public RegistrationFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        confirm_password = view.findViewById(R.id.confirmPassword);
        // TODO

    }
}



