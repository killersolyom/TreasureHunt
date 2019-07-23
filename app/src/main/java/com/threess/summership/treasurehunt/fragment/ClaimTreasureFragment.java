package com.threess.summership.treasurehunt.fragment;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
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
import com.threess.summership.treasurehunt.fragment.home_menu.FavoriteTreasureFragment;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;


public class ClaimTreasureFragment extends Fragment {

    public static String TAG = "claim_treasure_fragment";
    private HashMap<String, Treasure> myTestDatas;
    //Key:passcode   String: UserId

    private EditText myEditText;
    private Button myConfirmButton;
    private String passcode;
    private ImageView imageView;
    private ImageView mySuccsesfullImage;
    private Bundle arguments;
    private String myTreasureName;
    private String username;
    private Snackbar mySnackbar;

    Handler mHandler = new Handler();


    public ClaimTreasureFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        arguments = getArguments();
        myTreasureName = arguments.getString("treasureId");
        username=arguments.getString("username");


        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myEditText = view.findViewById(R.id.editText);
        myConfirmButton = view.findViewById(R.id.confirmButton);
        mySuccsesfullImage = view.findViewById(R.id.image_succsesfull_icon);
        mySnackbar = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "No internet conection!\n Please turn on the wifi", Snackbar.LENGTH_SHORT);
        imageView = view.findViewById(R.id.imageView2);
        getAllTreasuresServerCall();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).showHomeFragment();
            }
        });

    }

    public void confirmPasscode(@NonNull final View view) {
        final Snackbar mySnackbarError = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "Not vailable passcoe or wrong treasure!", Snackbar.LENGTH_SHORT);
        final Snackbar mySnackbarAvailable = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "This treasure passcode: '" + myTreasureName + "' is correct!", Snackbar.LENGTH_SHORT);
      //  final Snackbar mySnackbarAvailable2 = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "The Post is succes", Snackbar.LENGTH_SHORT);
        final Snackbar mySnackbarError2 = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "Not available treasure on server", Snackbar.LENGTH_SHORT);

        myConfirmButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {
                passcode = myEditText.getText().toString();

                final Treasure treasure = myTestDatas.get(passcode);
                if (treasure != null && treasure.getUsername().equals(myTreasureName)) {
                    mySnackbarAvailable.show();
                    showItems(view);

                    myConfirmButton.setVisibility(View.INVISIBLE);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideItems(view);
                            myConfirmButton.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                    TreasureClaim treasureClaim=new TreasureClaim(username,treasure.getPasscode());
                    ApiController.getInstance().createdTreasureClaim(treasureClaim, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            FragmentNavigation.getInstance(getContext()).showHomeFragment();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            //if not
                            mySnackbarError2.show();

                        }
                    });

                    FragmentNavigation.getInstance(getContext()).showHomeFragment();

                } else {
                    mySnackbarError.show();
                }

            }
        });

    }

    private void setMyTestDatas(ArrayList<Treasure> myTreasures) {
        myTestDatas = new HashMap<>();

        for (Treasure treasure : myTreasures) {
            myTestDatas.put(treasure.getPasscode(), treasure);
        }
    }

    private void showItems(@NonNull View view) {
        mySuccsesfullImage.setVisibility(View.VISIBLE);
    }

    private void hideItems(@NonNull View view) {
        mySuccsesfullImage.setVisibility(View.INVISIBLE);

    }


    private void getAllTreasuresServerCall(){
        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
                setMyTestDatas(response.body());
                confirmPasscode(getView());
            }

            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {
                mySnackbar.show();
            }
        });
    }

}
