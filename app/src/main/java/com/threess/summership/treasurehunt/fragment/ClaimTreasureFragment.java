package com.threess.summership.treasurehunt.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.qr_code_reader.QRCodeReader;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ClaimTreasureFragment extends Fragment {
    private static final String TAG = ClaimTreasureFragment.class.getSimpleName();

    private EditText myEditText;
    private Button myConfirmButton;
    private ImageView backImageButton;
    private Button qrCodeReaderButtn;
    private Treasure mTreasure;
    private View mView;
    private int QrRequestCode = 1;
    private String resultPassCodeFromQrCodeScanner = null;
    private boolean mHasQRCode = false;

    public ClaimTreasureFragment() {
    }

    @SuppressLint("ValidFragment")
    public ClaimTreasureFragment(Treasure treasure) {
        mTreasure = treasure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_claim_treasure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myEditText = view.findViewById(R.id.editText);
        myConfirmButton = view.findViewById(R.id.confirmButton);
        backImageButton = view.findViewById(R.id.imageView2);
        qrCodeReaderButtn = view.findViewById(R.id.qrCode_button);
        mView = view;
        backImageButton.setOnClickListener(view12 -> FragmentNavigation.getInstance(getContext()).popBackstack());
        qrCodeReaderButtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QRCodeReader.class);
            startActivityForResult(intent, QrRequestCode);
        });
        myConfirmButton.setOnClickListener(view1 ->
        {
            mHasQRCode = false;
            verifyResult();
        });
    }

    private void verifyResult(){
        if(mHasQRCode) {
            Log.e("3ss", mTreasure.getPasscode() + " " + resultPassCodeFromQrCodeScanner);
        }
        if(isValidTreasure()){
            SavedData sd = new SavedData(getContext());
            TreasureClaim treasureClaim=new TreasureClaim(sd.getCurrentUserName(),mTreasure.getPasscode());
            ApiController.getInstance().createdTreasureClaim(treasureClaim, new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Util.makeSnackbar( mView, R.string.Claim_Available , Snackbar.LENGTH_SHORT, R.color.blue300);
                    FragmentNavigation.getInstance(getContext()).popBackstack();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Util.makeSnackbar( mView, R.string.Claim_SnackBarError2, Snackbar.LENGTH_SHORT, R.color.orange900);
                }});
        }else{
            Util.makeSnackbar( mView, R.string.Claim_snackBarError1, Snackbar.LENGTH_LONG, R.color.orange900);
        }
    }

    private boolean isValidTreasure(){
        return (this.mTreasure!=null && !mTreasure.isClaimed())
                && ( this.mTreasure.getPasscode().equals(resultPassCodeFromQrCodeScanner)
                    || myEditText.getText().toString().trim().equals( this.mTreasure.getPasscode())
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            myEditText.setText(data.getStringExtra(QRCodeReader.RESULT_OF_QRCODE_READ));
            resultPassCodeFromQrCodeScanner = data.getStringExtra(QRCodeReader.RESULT_OF_QRCODE_READ);
            mHasQRCode = true;
            verifyResult();
        }
    }
}
