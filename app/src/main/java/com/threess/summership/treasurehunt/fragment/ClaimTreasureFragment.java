package com.threess.summership.treasurehunt.fragment;

import android.content.Intent;
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
import com.threess.summership.treasurehunt.util.Util;
import com.threess.summership.treasurehunt.qr_code_reader.QRCodeReader;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClaimTreasureFragment extends Fragment {
    public static final String TAG = ClaimTreasureFragment.class.getSimpleName();

    private HashMap<String, Treasure> myTestDatas;
    //Key:passcode   String: UserId

    private EditText myEditText;
    private Button myConfirmButton;
    private ImageView backImageButton;
    private ImageView mySuccsesfullImage;
    private Treasure treasure;
    private Button qrCodeReaderButtn;

    private final static  String KEYSTRINGTREASURE="treasureName";
    private final static String KEYSTRINGUSERNAME="username";

    Handler mHandler = new Handler();


    public ClaimTreasureFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.treasure = (Treasure) getArguments().getSerializable("Treasure");
        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myEditText = view.findViewById(R.id.editText);
        myConfirmButton = view.findViewById(R.id.confirmButton);
        mySuccsesfullImage = view.findViewById(R.id.image_succsesfull_icon);

        Util.makeSnackbar(view.findViewById(R.id.fragment_claim_treasure_id), R.string.Claim_SnackBarError_Internet, Snackbar.LENGTH_SHORT,R.color.red);
        backImageButton = view.findViewById(R.id.imageView2);
        getAllTreasuresServerCall();
        qrCodeReaderButtn = view.findViewById(R.id.qrCode_button);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).popBackstack();
            }
        });

        qrCodeReaderButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRCodeReader.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void confirmPasscode(@NonNull final View view) {

        myConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                if (myEditText.getText().toString().isEmpty()) {
                    myEditText.requestFocus();
                    myEditText.setError(getString(R.string.Claim_editText_EmptyError));
                    return;
                }
                else{
                String passcode = myEditText.getText().toString();
                final Treasure treasure = myTestDatas.get(passcode);
                if (isTheSameTreasure(treasure)) {
                    Util.makeSnackbar( view.findViewById(R.id.fragment_claim_treasure_id), getString(R.string.Claim_Available) + treasure.getUsername() + getString(R.string.Claim_Available2), Snackbar.LENGTH_SHORT, R.color.red);
                    showItems(view);
                    myConfirmButton.setVisibility(View.INVISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideItems(view);
                            myConfirmButton.setVisibility(View.VISIBLE);

                            TreasureClaim treasureClaim=new TreasureClaim(treasure.getUsername(),treasure.getPasscode());
                            ApiController.getInstance().createdTreasureClaim(treasureClaim, new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    FragmentNavigation.getInstance(getContext()).showHomeFragment();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Util.makeSnackbar( view.findViewById(R.id.fragment_claim_treasure_id), getString(R.string.Claim_SnackBarError2), Snackbar.LENGTH_SHORT, R.color.red);

                                }
                            });
                        }
                    }, 2500);

                } else {
                    Util.makeSnackbar(view.findViewById(R.id.fragment_claim_treasure_id),getString(R.string.Claim_snackBarError1), Snackbar.LENGTH_SHORT, R.color.red);
                }

            }
        }

        });


    }
    private boolean isTheSameTreasure(Treasure treasure) {
        return (treasure!=null && treasure == this.treasure);
    }
    public static ClaimTreasureFragment newInstance(Treasure treasure){

        ClaimTreasureFragment claimTreasureFragment=new ClaimTreasureFragment();
        Bundle args=new Bundle();
        args.putSerializable("Treasure",treasure);
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
                //Util.makeSnackbar(this.view.findViewById(R.id.fragment_claim_treasure_id), R.string.Claim_SnackBarError_Internet, Snackbar.LENGTH_SHORT,R.color.red);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String code = data.getStringExtra(QRCodeReader.RESULT_OF_QRCODE_READ);
        myEditText.setText(code);
    }
}
