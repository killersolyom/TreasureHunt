package com.threess.summership.treasurehunt.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    public ClaimTreasureFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myTreasureName = getArguments().getString(Constants.KEY_STRING_TREASURE);
        username=getArguments().getString(Constants.KEY_STRING_USERNAME);
        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
        // Do not modify!

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myEditText = view.findViewById(R.id.editText);
        myConfirmButton = view.findViewById(R.id.confirmButton);
        mySuccsesfullImage = view.findViewById(R.id.image_succsesfull_icon);
        mySnackbar = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), getString(R.string.Claim_SnackBarError_Internet), Snackbar.LENGTH_SHORT);
        imageView = view.findViewById(R.id.imageView2);
        getAllTreasuresServerCall();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).showHomeFragment();
            }
        });

    }

    private void confirmPasscode(@NonNull final View view) {
        final Snackbar mySnackbarError = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id),getString(R.string.Claim_snackBarError1), Snackbar.LENGTH_SHORT);
        final Snackbar mySnackbarAvailable = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id),getString(R.string.Claim_Available) + myTreasureName + getString(R.string.Claim_Available2), Snackbar.LENGTH_SHORT);
      //  final Snackbar mySnackbarAvailable2 = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), "The Post is succes", Snackbar.LENGTH_SHORT);
        final Snackbar mySnackbarError2 = Snackbar.make(view.findViewById(R.id.fragment_claim_treasure_id), getString(R.string.Claim_SnackBarError2), Snackbar.LENGTH_SHORT);

        myConfirmButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {

                if (myEditText.getText().toString().isEmpty()) {
                    myEditText.requestFocus();
                    myEditText.setError(getString(R.string.Claim_editText_EmptyError));
                    return;
                }
                else{
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
                        }
                    }, 2500);

                   // FragmentNavigation.getInstance(getContext()).showHomeFragment();
                } else {
                    mySnackbarError.show();
                }

            }
        }});


    }
    public static ClaimTreasureFragment newInstance(String newKeyStringTreasure,String newKeyStringUsername){

        ClaimTreasureFragment claimTreasureFragment=new ClaimTreasureFragment();
        Bundle args=new Bundle();
        args.putString(Constants.KEY_STRING_TREASURE,newKeyStringTreasure);
        args.putString(Constants.KEY_STRING_USERNAME,newKeyStringUsername);
        claimTreasureFragment.setArguments(args);

        return claimTreasureFragment;


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
