package com.threess.summership.treasurehunt.util;

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


    public static class ApiController {
        public static final String BASE_URL = "http://5.254.125.248:3000/";
    }


    public static class SavedData {
        public static final String SHARED_PREFERENCE_KEY = "TreasureHunt";
        public static final String PROFILE_IMAGE_KEY = "profile_image_key";
        public static final String USER_PROFILE_NAME_KEY = "profile_name_key";
        public static final String USER_PASSWORD_KEY = "user_password_key";
        public static final String REMEMBER_ME_SWITCH_KEY = "RememberMeSwitch";
        public static final String AUTO_LOGIN_SWITCH_KEY = "AutoLoginSwitch";
    }


}
