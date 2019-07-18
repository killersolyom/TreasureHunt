package com.threess.summership.treasurehunt.logic;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.service.TreasuresRetrofitService;
import com.threess.summership.treasurehunt.service.UserRetrofitService;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private static ApiController sInstance = null;
    private static final String TAG = "ApiController";
    private static String BASE_URL = "http://5.254.125.248:3000/";
    private TreasuresRetrofitService mTreasureService;
    private UserRetrofitService mUserService;

    /**
     * Returns the ApiController instance.
     * @return ApiController instance
     */
    public static ApiController getInstance(){
        if( sInstance == null ){
            sInstance = new ApiController();
            Retrofit  mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl( BASE_URL )
                    .build();
            sInstance.mUserService = mRetrofit.create(UserRetrofitService.class);
            sInstance.mTreasureService = mRetrofit.create(TreasuresRetrofitService.class);
        }
        return sInstance;
    }


    /**
     * This method gets all treasures from the API.
     * @param callback callback with the list of the treasures
     */
    public void getAllTreasures(final Callback<ArrayList<Treasure>> callback){
        mTreasureService.allExistingTreasureList().enqueue( callback );
    }

    public void loginUser(final User user, final Callback<Object> callback){
        mUserService.loginUser(user).enqueue(callback);
    }

    public void registerUser(final User user, final Callback<Object> callback){
        mUserService.createUser(user).enqueue(callback);
    }

    // TODO: user service

}
