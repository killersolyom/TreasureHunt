package com.threess.summership.treasurehunt.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.qr_code_reader.QRCodeReader;
import com.threess.summership.treasurehunt.util.Animator;
import com.threess.summership.treasurehunt.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ClaimTreasureFragment extends Fragment {
    private static final String TAG = ClaimTreasureFragment.class.getSimpleName();

    private EditText myEditText;
    private TextView mDescriptionText;
    private Button myConfirmButton;
    private ImageView backImageButton;
    private Button qrCodeReaderButtn;
    private Treasure mTreasure;
    private View mView;
    private int QrRequestCode = 1;
    private String resultPassCodeFromQrCodeScanner = null;
    private boolean mHasQRCode = false;
    Handler mHandler;

    public ClaimTreasureFragment() {}

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
        mHandler=new Handler();
        myEditText = view.findViewById(R.id.editText);
        myConfirmButton = view.findViewById(R.id.confirmButton);
        backImageButton = view.findViewById(R.id.imageView2);
        qrCodeReaderButtn = view.findViewById(R.id.qrCode_button);
        mDescriptionText=view.findViewById(R.id.textView);
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
        Log.e("3ss",mTreasure.getPasscode());
        if (isValidTreasure()) {
            playSuccessImageAnimation();
            SavedData sd = new SavedData(getContext());
            TreasureClaim treasureClaim = new TreasureClaim(sd.getUserName(), mTreasure.getPasscode());
            sd.setScore((float)(mTreasure.getPrizePoints()+ sd.getScore()));
            ApiController.getInstance().createdTreasureClaim(treasureClaim, new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Util.makeSnackbar(mView, R.string.Claim_Available, Snackbar.LENGTH_SHORT, R.color.green);
                    playSuccessImageAnimation();
                    mHandler.postDelayed(() -> {
                        //new score value
                        scoreUpdate(sd,mTreasure.getPrizePoints()+sd.getScore());

                    },3500);
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Util.makeSnackbar(mView, R.string.claim_snack_bar_error_treasure_not_available, Snackbar.LENGTH_SHORT, R.color.orange900);
                    Log.e("boti", "failureFirst");
                    FragmentNavigation.getInstance(getContext()).popBackstack();
                }
            });
        }

    }

    private void playSuccessImageAnimation() {
        ImageView im = mView.findViewById(R.id.image_succsesfull_icon);
        hideItems();

        Animator animator= new Animator(getContext(),im,true);
        animator.AddScale(0,1.2f,0,1.2f,.5f,.5f,1000);
        animator.AddAlpha(1,0.40f, 0,false,6000);
        animator.Start();

        Animator animator2 = new Animator(getContext(),im);
        animator2.AddScale(1.2f,0.75f,1.2f,0.75f,.5f,.5f,1000);
        animator.AddAlpha(0.25f,1,0,true,3000);
        animator2.Start(2000);

        mHandler.postDelayed(() -> im.setVisibility(View.INVISIBLE),1000);
    }

    private boolean isValidTreasure(){
        SavedData sd = new SavedData(getContext());
        boolean ok= ((this.mTreasure!=null && !mTreasure.isClaimed()
                && (this.mTreasure.getPasscode().equals(resultPassCodeFromQrCodeScanner)))
                    || (myEditText.getText().toString().trim().equals( this.mTreasure.getPasscode())
                        && !this.mTreasure.getUsername().equals(sd.getUserName()))
        );
        if(!ok && this.mTreasure.getUsername().equals(sd.getUserName())){
            Util.makeSnackbar(mView,R.string.Claim_error3,Snackbar.LENGTH_SHORT,R.color.orange900);
        }
        if(!ok && !(myEditText.getText().toString().trim().equals( this.mTreasure.getPasscode()))){
            Util.makeSnackbar( mView, R.string.Claim_snackBarError1, Snackbar.LENGTH_LONG, R.color.orange900);
        }
        return  ok;
    }
    private void scoreUpdate(SavedData sd,Double newScore){
        ApiController.getInstance().updateScore(sd.getUserName(), newScore, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call1, Response<Object> response1) {
                try {
                    Util.makeSnackbar(mView, R.string.Claim_snackBar_scoreUpdate, Snackbar.LENGTH_SHORT, R.color.blue300);
                }catch (Exception ignored){}
                FragmentNavigation.getInstance(getContext()).popBackstack();
            }

            @Override
            public void onFailure(Call<Object> calll, Throwable t) {
                Util.makeSnackbar(mView,R.string.claim_error_no_score_change,Snackbar.LENGTH_SHORT,R.color.orange900);
                FragmentNavigation.getInstance(getContext()).popBackstack();
            }
        });
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

    public void hideItems(){
        backImageButton.setVisibility(mView.INVISIBLE);
        qrCodeReaderButtn.setVisibility(mView.INVISIBLE);
        myConfirmButton.setVisibility(mView.INVISIBLE);
        myEditText.setVisibility(mView.INVISIBLE);
        mDescriptionText.setVisibility(mView.INVISIBLE);
    }

}
