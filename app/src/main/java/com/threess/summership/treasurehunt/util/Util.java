package com.threess.summership.treasurehunt.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public final class Util {

    public static void hideKeyboard(Context context, Button button){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(button.getWindowToken(), 0);
    }

}
