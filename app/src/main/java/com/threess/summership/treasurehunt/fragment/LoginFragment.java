package com.threess.summership.treasurehunt.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Animator;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();

    private EditText nameText, passwordText;
    private TextView createAccountLabel;
    private Switch rememberMeSwitch, autoLoginSwitch;
    private SavedData dataManager;
    private String userName, userPassword;
    private Button login;
    private User user;

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

        login = view.findViewById(R.id.LogIn);
        dataManager = new SavedData(getContext());
        nameText = view.findViewById(R.id.loginName);
        passwordText = view.findViewById(R.id.loginPassword);
        rememberMeSwitch = view.findViewById(R.id.remember);
        autoLoginSwitch = view.findViewById(R.id.autologin);
        createAccountLabel = view.findViewById(R.id.createAccount);
        userName = dataManager.readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY);
        userPassword = dataManager.readStringData(Constant.SavedData.USER_PASSWORD_KEY);
        loadSettings();

        playAnimations(view);

        rememberMeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                dataManager.writeBooleanData(Constant.SavedData.REMEMBER_ME_SWITCH_KEY, isChecked));
        autoLoginSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                dataManager.writeBooleanData(Constant.SavedData.AUTO_LOGIN_SWITCH_KEY, isChecked));
        passwordText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                dataManager.writeStringData(passwordText.getText().toString(), Constant.SavedData.USER_PASSWORD_KEY);
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                dataManager.writeStringData(nameText.getText().toString(), Constant.SavedData.USER_PROFILE_NAME_KEY);
            }
        });
        createAccountLabel.setOnClickListener(v ->
                FragmentNavigation.getInstance(getContext()).showRegisterFragment());

        login.setOnClickListener(view1 -> login());

    }

    private void login() {
        Util.hideKeyboard(getContext(), login);
        user = new User(nameText.getText().toString().trim(), passwordText.getText().toString().trim());
        ApiController.getInstance().loginUser(user, new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @Nullable Response<Object> response) {
                if (response.code() == Constant.LogIn.goodResponseCode) {
                    Util.makeSnackbar(getView(), R.string.successful, Snackbar.LENGTH_SHORT, R.color.blue300);
                    FragmentNavigation.getInstance(getContext()).showHomeFragment();
                } else {
                    Util.errorHandling(getView(),response.errorBody().source().toString(),response.code());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void loadSettings() {
        if (dataManager.getRememberMeSwitch()) {
            rememberMeSwitch.setChecked(true);
            nameText.setText(userName);
            passwordText.setText(userPassword);

            if (dataManager.getAutoLoginSwitch()) {
                autoLoginSwitch.setChecked(true);
                login();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        loadSettings();
    }

    private void playAnimations(View view) {
        Context c = getContext();
        int dMs = 1000;       // duration in mc
        int dbaMs = 100;      // duration between animations in ms
        int fromXDp = -500;   // distance from elements fly in

        Animator logoAnim = new Animator(c, view.findViewById(R.id.imageView), true);
        logoAnim.AddIntroSet();

        Animator nameAnim = new Animator(c, nameText, true);
        nameAnim.AddSlide(fromXDp, 0, 0, 0, dMs);
        nameAnim.AddScale(8f, 1f, 8f, 1f, .5f, .5f, dMs);

        Animator passAnim = new Animator(c, passwordText, true);
        passAnim.AddSlide(fromXDp, 0, 0, 0, dMs);
        passAnim.AddScale(8f, 1f, 8f, 1f, .5f, .5f, dMs);

        Animator buttonAnim = new Animator(c, login, true);
        buttonAnim.AddSlide(fromXDp, 0, 0, 0, dMs);
        buttonAnim.AddScale(8f, 1f, 8f, 1f, .5f, .5f, dMs);

        Animator rememberAnim = new Animator(c, rememberMeSwitch, true);
        rememberAnim.AddSlide(fromXDp, 0, 0, 0, dMs);
        rememberAnim.AddScale(8f, 1f, 8f, 1f, .5f, .5f, dMs);

        Animator autologinAnim = new Animator(c, autoLoginSwitch, true);
        autologinAnim.AddSlide(fromXDp, 0, 0, 0, dMs);
        autologinAnim.AddScale(8f, 1f, 8f, 1f, .5f, .5f, dMs);

        Animator createAccAnim = new Animator(c, createAccountLabel, true);
        createAccAnim.AddSlide(0,0,200,0,dMs);

        logoAnim.Start();
        nameAnim.Start(dbaMs);
        passAnim.Start(2*dbaMs);
        buttonAnim.Start(3*dbaMs);
        rememberAnim.Start(4*dbaMs);
        autologinAnim.Start(5*dbaMs);
        createAccAnim.Start(6*dbaMs);

    }

}
