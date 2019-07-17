package com.threess.summership.treasurehunt.fragment;

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
import android.widget.Toast;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;


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


        usernameText.setText("");
        passwordText.setText("");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(usernameText.getText().toString(), passwordText.getText().toString(), confirm_passwordText.getText().toString());
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        // TODO

    }

    private void validateUser (String username, String password, String confirm_password){
        String errors = checkUsername(username);
        errors += checkPassword(password);
        errors += checkConfirmPassword(password,confirm_password);
        if (!errors.equals("")){
            Snackbar snackbar = Snackbar.make(getView(),errors,Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            dataManager.writeStringData(usernameText.getText().toString(),"UserName");
            dataManager.writeStringData(passwordText.getText().toString(),"UserPassword");
        }
    }

    private String checkUsername(String username){
        if (username.length() < 5 || username.length() > 10 || username.contains(" ")){
            return "Invalid username ";
        }
        return "";
    }

    private String checkPassword(String password){
        if (password.length() < 8 || password.length() > 16){
            return "Invalid password ";
        }
        return "";
    }

    private String checkConfirmPassword (String password, String confirm_password){
        if (!confirm_password.equals(password)){
            return "The passwords do not match!";
        }
        return "";
    }
}



