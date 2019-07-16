package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;


import com.threess.summership.treasurehunt.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ClaimTreasureFragment extends Fragment {

    public static String TAG = "claim_treasure_fragment";
    private static final String FILE_NAME="example.txt";
    private HashMap<String,Integer> myTestDatas;

    private EditText myEditText;
    private Button myConfirmButton;
    private String passcode;
    private ImageView imageView;

    public ClaimTreasureFragment() {
        // constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myEditText= view.findViewById(R.id.editText);
        myConfirmButton=view.findViewById(R.id.confirmButton);
        setMyTestDatas();

        confirmPasscode(view);

        imageView=view.findViewById(R.id.imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fr = new HomeFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragment_claim_treasure_id, fr);
                fragmentTransaction.commit();
            }
        });






    }

    public void confirmPasscode(@NonNull final View view){

      //  final String passcode= myEditText.getText().toString();

        final Snackbar mySnackbarError = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "Not vailable passcoe!",  Snackbar.LENGTH_SHORT);
        final Snackbar mySnackbarAvailable = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "Correct!",  Snackbar.LENGTH_SHORT);


        myConfirmButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                passcode= myEditText.getText().toString();

                if( myTestDatas.containsKey(passcode)){
                    mySnackbarAvailable.show();
                }
                else{
                   mySnackbarError.show();
                }


            }
        });






    }

    public void setMyTestDatas(){

        myTestDatas=new HashMap<>();
        myTestDatas.put("aaa",0);
        myTestDatas.put("aab",1);
        myTestDatas.put("aac",2);
        myTestDatas.put("aaf",3);
        myTestDatas.put("aad",4);
        myTestDatas.put("aak",5);
        myTestDatas.put("aao",6);
        myTestDatas.put("aaaa",7);
        myTestDatas.put("aaa123",8);
        myTestDatas.put("aaa1234",9);
        myTestDatas.put("aaa152",10);
        myTestDatas.put("aaa162",11);


    }
}
