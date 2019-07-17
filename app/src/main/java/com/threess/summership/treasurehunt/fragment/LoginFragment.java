package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.home.fragment.HomeFragment;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.service.UserRetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {

    public static String TAG = "login_fragment";
    private EditText nameText, passwordText;
    private TextView createAccountLabel;
    private Switch rememberMeSwitch, autoLoginSwitch;
    private SavedData dataManager;
    private String userName,userPassword;
    private Button login;
    private User user;

    private Retrofit retrofit;

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

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UserRetrofitService.BASE_URL)
                .build();



        login = view.findViewById(R.id.LogIn);
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
                FragmentNavigation.getInstance(getContext()).showRegisterFragment();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login(){
        user = new User(nameText.getText().toString().trim(),passwordText.getText().toString().trim());
        UserRetrofitService service = retrofit.create(UserRetrofitService.class);
        service.loginUser(user).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @Nullable Response<Object> response) {
                //200 jo
                if (response.code()==200){
                    Snackbar snackbar = Snackbar.make(getView(),"Successful", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    //Toast.makeText(getActivity().getBaseContext(),"Successful",Toast.LENGTH_LONG).show();
                    FragmentNavigation.getInstance(getContext()).showHomeFragment();
                } else {
                    //Toast.makeText(getActivity().getBaseContext(),"User not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(getView(),"User not found", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, Throwable t) {
                Log.e(TAG, "Host failure", t);
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
            login();
        }
    }
}
