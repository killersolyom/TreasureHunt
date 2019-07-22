package com.threess.summership.treasurehunt.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.threess.summership.treasurehunt.R;

import java.util.Random;

public final class Util {

    public static void hideKeyboard(Context context, Button button){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(button.getWindowToken(), 0);
    }

    public static void makeSnackbar(View view, int textId, int length, int colorId){
        Snackbar snackbar = Snackbar.make(view,textId,length);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),colorId));
        snackbar.show();
    }

    public static Bitmap getDrawableTreasureImage(Context context){
        return Bitmap.createScaledBitmap(randomBitmap(context),100,100,false);
    }

    private static Bitmap randomBitmap(Context context){
        switch (new Random().nextInt(5)){
            case 0:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t1);
            case 1:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t2);
            case 2:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t3);
            case 3:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t4);
            case 4:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t5);
            default:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.t1);
        }
    }

}
