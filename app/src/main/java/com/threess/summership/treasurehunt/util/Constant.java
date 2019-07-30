package com.threess.summership.treasurehunt.util;

import android.util.SparseIntArray;
import android.view.Surface;

import com.google.android.gms.common.api.Api;
import com.threess.summership.treasurehunt.fragment.ClaimTreasureFragment;

public class Constant {

    public static class Common{
        public static final int PERMISSION_REQUEST_CODE = 10;
        public static final int REQUEST_LOCATION = 1;
        public static final int GALLERY_REQUEST_CODE = 862;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }


    public static class ClaimTreasure {
        public static final String KEY_STRING_TREASURE = "treasureName";
        public static final String KEY_STRING_USERNAME = "username";
    }

    public static class HideTreasure {
        public static final int HIDE_TREASURE_REQUEST_CODE = 400;
        public static final String ALREADY_EXISTS= "already exists";
        public static final String ALL_FIELDS_ARE_REQUIRED = "All the fields are required";
    }

    public static class ApiController {
        public static final String BASE_URL = "http://5.254.125.248:3000/";
    }

    public static class Registration {
        public static final int REGISTRATION_REQUEST_CODE = 500;
        public static final int goodResponseCode = 200;
        public static final String ALREADY_EXISTS = "Username already exists.";
    }

    public static class LogIn {
        public static final int LOGIN_REQUEST_CODE = 403;
        public static final int goodResponseCode = 200;
        public static final String INCORRECT_PASSWORD = "Incorrect password for username";
        public static final String USERNAME_NOT_EXISTS = "does not exist.";
    }

    public static class SavedData {
        public static final String SHARED_PREFERENCE_KEY = "TreasureHunt";
        public static final String PROFILE_IMAGE_KEY = "profile_image_key";
        public static final String USER_PROFILE_NAME_KEY = "profile_name_key";
        public static final String USER_PASSWORD_KEY = "user_password_key";
        public static final String REMEMBER_ME_SWITCH_KEY = "RememberMeSwitch";
        public static final String AUTO_LOGIN_SWITCH_KEY = "AutoLoginSwitch";
    }

    public static class Camera {
        /** Camera state: Showing camera preview. */
        public static final int STATE_PREVIEW = 0;

        /** Camera state: Waiting for the focus to be locked. */
        public static final int STATE_WAITING_LOCK = 1;

        /** Camera state: Waiting for the exposure to be precapture state. */
        public static final int STATE_WAITING_PRECAPTURE = 2;

        /** Camera state: Waiting for the exposure state to be something other than precapture. */
        public static final int STATE_WAITING_NON_PRECAPTURE = 3;

        /** Camera state: Picture was taken. */
        public static final int STATE_PICTURE_TAKEN = 4;

        /** States for the flash */
        public static final int FLASH_STATE_OFF = 0;
        public static final int FLASH_STATE_ON = 1;
        public static final int FLASH_STATE_AUTO = 2;

        public static final int REQUEST_CAMERA_PERMISSION = 1;
        public static final String FRAGMENT_DIALOG = "dialog";

        /** Time it takes for icons to fade (in milliseconds) */
        public static final int ICON_FADE_DURATION  = 400;

        public static final SparseIntArray ORIENTATIONS = new SparseIntArray();
        static {
            ORIENTATIONS.append(Surface.ROTATION_0, 90);
            ORIENTATIONS.append(Surface.ROTATION_90, 0);
            ORIENTATIONS.append(Surface.ROTATION_180, 270);
            ORIENTATIONS.append(Surface.ROTATION_270, 180);
        }
    }

}
