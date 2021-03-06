package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {
    private static final String TAG = RegistrationFragment.class.getSimpleName();

    private EditText usernameText, passwordText, confirm_passwordText;
    private Button register, cancel;

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
        usernameText = view.findViewById(R.id.username);
        passwordText = view.findViewById(R.id.password);
        confirm_passwordText = view.findViewById(R.id.confirmPassword);
        register = view.findViewById(R.id.register);
        cancel = view.findViewById(R.id.cancel);
        register.setOnClickListener(view12 ->
                validateUser(usernameText.getText().toString().trim(),
                        passwordText.getText().toString().trim(),
                        confirm_passwordText.getText().toString().trim()));


        cancel.setOnClickListener(view1 -> {
            Util.hideKeyboard(getContext(), cancel);
            getFragmentManager().popBackStack();
        });

    }


    private void validateUser(String username, String password, String confirm_password) {
        Util.hideKeyboard(getContext(), register);
        String error = checkUsername(username);
        if (!error.isEmpty()) {
            usernameText.setError(error);
            return;
        }
        error = checkPassword(password);
        if (!error.isEmpty()) {
            passwordText.setError(error);
            return;
        }
        error = checkConfirmPassword(password, confirm_password);
        if (!error.isEmpty()) {
            confirm_passwordText.setError(error);
            return;
        }
        User user = new User(username, password);
        ApiController.getInstance().registerUser(user, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == Constant.Registration.goodResponseCode) {
                    Util.makeSnackbar(getView(), R.string.successful, Snackbar.LENGTH_LONG, R.color.blue300);
                    new SavedData(getContext()).setUserDataAfterRegistration(username,password);
                    getFragmentManager().popBackStack();
                } else {
                    Util.errorHandling(getView(),response.errorBody().source().toString(),response.code());
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Util.makeSnackbar(getView(), R.string.server_not_found, Snackbar.LENGTH_LONG, R.color.orange700);
            }
        });
    }

    private String checkUsername(String username) {
        if (username.length() < 5 || username.length() > 10 || username.contains(" ")) {
            return getString(R.string.invalid_username) + " ";
        }
        return "";
    }

    private String checkPassword(String password) {
        if (password.length() < 6 || password.length() > 16) {
            return getString(R.string.invalid_password) + " ";
        }
        return "";
    }

    private String checkConfirmPassword(String password, String confirm_password) {
        if (!confirm_password.equals(password)) {
            return getString(R.string.invalid_password_confirm) + " ";
        }
        return "";
    }
}



