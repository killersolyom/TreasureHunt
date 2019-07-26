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
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;
import com.threess.summership.treasurehunt.qr_code_reader.QRCodeReader;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ClaimTreasureFragment extends Fragment {
    public static final String TAG = ClaimTreasureFragment.class.getSimpleName();

   // private HashMap<String, Treasure> myTestDatas;
    //Key:passcode   String: UserId

    private EditText mEditText;
    private Button mConfirmButton;
    private String mPasscode;
    private ImageView mBackImageButton;
    private ImageView mSuccsesfullImage;
    private Treasure mTreasure;
    private Button qrCodeReaderButtn;

    Handler mHandler = new Handler();
    
    public ClaimTreasureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mTreasure = (Treasure) getArguments().getSerializable("Treasure");
        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.editText);
        mConfirmButton = view.findViewById(R.id.confirmButton);
        mSuccsesfullImage = view.findViewById(R.id.image_succsesfull_icon);
        mBackImageButton = view.findViewById(R.id.imageView2);
        qrCodeReaderButtn = view.findViewById(R.id.qrCode_button);

        mBackImageButton.setOnClickListener(new View.OnClickListener() {
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
        confirmPasscode(view);

    }

    private void confirmPasscode(@NonNull final View view) {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (mEditText.getText().toString().isEmpty()) {
                    mEditText.requestFocus();
                    mEditText.setError(getString(R.string.Claim_editText_EmptyError));
                    return;
                }
                else{
                mPasscode = mEditText.getText().toString();
                if (isTheSamePasscode(mPasscode)) {
                    Util.makeSnackbar( view.getRootView().findViewById(R.id.fragment_claim_treasure_id), R.string.Claim_Available , Snackbar.LENGTH_SHORT, R.color.red);
                    showItems(view);
                    mConfirmButton.setVisibility(View.INVISIBLE);
                    qrCodeReaderButtn.setVisibility(View.INVISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideItems(view);
                            mConfirmButton.setVisibility(View.VISIBLE);
                            TreasureClaim treasureClaim=new TreasureClaim(mTreasure.getUsername(),mTreasure.getPasscode());
                            ApiController.getInstance().createdTreasureClaim(treasureClaim, new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    FragmentNavigation.getInstance(getContext()).showHomeFragment();
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Util.makeSnackbar( view.getRootView().findViewById(R.id.fragment_claim_treasure_id), R.string.Claim_SnackBarError2, Snackbar.LENGTH_SHORT, R.color.red);
                                }
                            });
                        }
                    }, 1500);
                } else {
                    Util.makeSnackbar(view.getRootView().findViewById(R.id.fragment_claim_treasure_id),R.string.Claim_snackBarError1, Snackbar.LENGTH_SHORT, R.color.red);
                }
            }
        }
        });
    }

    private boolean isTheSamePasscode(String mPasscode) {
        return (mPasscode!=null && mPasscode.equals(mTreasure.getPasscode()));
    }

    public static ClaimTreasureFragment newInstance(Treasure treasure){
        ClaimTreasureFragment claimTreasureFragment=new ClaimTreasureFragment();
        Bundle args=new Bundle();
        args.putSerializable("Treasure",treasure);
        claimTreasureFragment.setArguments(args);
        return claimTreasureFragment;
    }
//
//    private void setMyTestDatas(ArrayList<Treasure> myTreasures) {
//        myTestDatas = new HashMap<>();
//
//        for (Treasure treasure : myTreasures) {
//            myTestDatas.put(treasure.getPasscode(), treasure);
//        }
//    }

    private void showItems(@NonNull View view) {
        mSuccsesfullImage.setVisibility(View.VISIBLE);
    }
    private void hideItems(@NonNull View view) { mSuccsesfullImage.setVisibility(View.INVISIBLE); }


//    private void getAllTreasuresServerCall(){
//        ApiController.getInstance().getAllTreasures(new Callback<ArrayList<Treasure>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Treasure>> call, Response<ArrayList<Treasure>> response) {
//                setMyTestDatas(response.body());
//                confirmPasscode(getView());
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {
//                //Util.makeSnackbar(this.view.findViewById(R.id.fragment_claim_treasure_id), R.string.Claim_SnackBarError_Internet, Snackbar.LENGTH_SHORT,R.color.red);
//            }
//        });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mEditText.setText(data.getStringExtra(QRCodeReader.RESULT_OF_QRCODE_READ));
        }
    }
}
