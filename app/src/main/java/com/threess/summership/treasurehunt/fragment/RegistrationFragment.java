package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    public static String TAG = "registration_fragment";
    private EditText usernameText, passwordText, confirm_passwordText;
    private Button  register, cancel;
    private SavedData dataManager;

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
        dataManager = new SavedData(getContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(usernameText.getText().toString().trim(), passwordText.getText().toString().trim(), confirm_passwordText.getText().toString().trim());
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.hideKeyboard(getContext(),cancel);
                getFragmentManager().popBackStack();
            }
        });

        // TODO

    }



    private void validateUser (String username, String password, String confirm_password){
        Util.hideKeyboard(getContext(),register);
        String errors = checkUsername(username);
        errors += checkPassword(password);
        errors += checkConfirmPassword(password,confirm_password);
        if (!errors.equals("")){
            Snackbar snackbar = Snackbar.make(getView(),errors,Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            dataManager.writeStringData(usernameText.getText().toString(),"UserName");
            dataManager.writeStringData(passwordText.getText().toString(),"UserPassword");
            User user = new User(username,password);
            ApiController.getInstance().registerUser(user,new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.code() == 200){
                        Snackbar snackbar = Snackbar.make(getView(), R.string.successful,Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.green));
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(getView(),R.string.registration_failed,Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        snackbar.show();
                    }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            });
        }
    }

    private String checkUsername(String username){
        if (username.length() < 5 || username.length() > 10 || username.contains(" ")){
            return getString(R.string.invalid_username) + " ";
        }
        return "";
    }

    private String checkPassword(String password){
        if (password.length() < 6 || password.length() > 16){
            return getString(R.string.invalid_password) + " ";
        }
        return "";
    }

    private String checkConfirmPassword (String password, String confirm_password){
        if (!confirm_password.equals(password)){
            return getString(R.string.invalid_passwordconfirm) + " ";
        }
        return "";
    }
}



