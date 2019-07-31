package com.threess.summership.treasurehunt.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.camera.CameraActivity;
import com.threess.summership.treasurehunt.fragment.home_menu.ProfileFragment;

import static com.threess.summership.treasurehunt.util.Constant.Common.REQUEST_IMAGE_CAPTURE;

public class ChooserDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle(getActivity().getString(R.string.upload_profile_image))
                .setMessage(getActivity().getString(R.string.Chooser_dialog_message))
                .setNegativeButton(getActivity().getString(R.string.choose_from_gallery), (dialog, which) -> ProfileFragment.sInstance.pickFromGallery())
                .setPositiveButton(getActivity().getString(R.string.take_picture), (dialog, which)
                        -> {
                    Intent takePictureIntent = new Intent(getActivity(), CameraActivity.class);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        ProfileFragment.sInstance.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                })
                .setNeutralButton(getActivity().getString(R.string.cancel),(dialog, which) -> dialog.dismiss())
                .create();
    }


}
