package com.threess.summership.treasurehunt.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public final class Util {

    public static void hideKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
    }

}
