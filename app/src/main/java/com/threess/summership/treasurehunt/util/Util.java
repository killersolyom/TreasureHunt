package com.threess.summership.treasurehunt.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

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

}
