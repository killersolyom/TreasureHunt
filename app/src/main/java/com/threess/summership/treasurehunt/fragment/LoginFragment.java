package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

import java.util.NavigableMap;


public class LoginFragment extends Fragment {

    public static String TAG = "login_fragment";
    private EditText nameText, passwordText;
    private TextView createAccountLabel;
    private Switch rememberMeSwitch, autoLoginSwitch;
    private SavedData dataManager;
    private String userName,userPassword;

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
        createAccountLabel = view.findViewById(R.id.createAccount);
        userName = dataManager.readStringData("UserName");
        userPassword = dataManager.readStringData("UserPassword");
        loadSettings();

        rememberMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataManager.writeBooleanData(isChecked,"RememberMeSwitch");
            }
        });
        autoLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataManager.writeBooleanData(isChecked,"AutoLoginSwitch");
            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            public void afterTextChanged(Editable s) {
                dataManager.writeStringData(passwordText.getText().toString(),"UserPassword");
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            public void afterTextChanged(Editable s) {
                dataManager.writeStringData(nameText.getText().toString(),"UserName");
            }
        });
        createAccountLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNavigation.getInstance().replaceFragment(new RegistrationFragment());
            }
        });



    }

    private void loadSettings(){
        if(dataManager.readBooleanData("RememberMeSwitch")){
            rememberMeSwitch.setChecked(true);
            nameText.setText(userName);
            passwordText.setText(userPassword);
        }
        if(dataManager.readBooleanData("AutoLoginSwitch")){
            autoLoginSwitch.setChecked(true);
            //TODO database querry
        }
    }
}
