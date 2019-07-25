package com.threess.summership.treasurehunt.logic;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.service.TreasuresRetrofitService;
import com.threess.summership.treasurehunt.service.UserRetrofitService;
import com.threess.summership.treasurehunt.util.Constant;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    public static final String TAG = ApiController.class.getSimpleName();

    private static ApiController sInstance = null;
    private static Retrofit mRetrofit;

    private TreasuresRetrofitService mTreasureService;
    private UserRetrofitService mUserService;
    private TreasuresRetrofitService mClaimedTreasure;

    private ApiController() {

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl( Constant.ApiController.BASE_URL )
                .build();
        mTreasureService = mRetrofit.create(TreasuresRetrofitService.class);
        mUserService = mRetrofit.create(UserRetrofitService.class);
        mClaimedTreasure = mRetrofit.create(TreasuresRetrofitService.class);
    }

    /**
     * Returns the ApiController instance.
     * @return ApiController instance
     */
    public static ApiController getInstance(){
        if( sInstance == null ){
            sInstance = new ApiController();
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

    public void getClaimedTreasures(final String userName, final Callback<ArrayList<Treasure>> callback){
        mClaimedTreasure.claimedTreasureByUser(userName).enqueue(callback);
    }

    public void loginUser(final User user, final Callback<Object> callback){
     //   Log.d("HALASZ", user.getPassword() + "   " + user.getUsername());
        mUserService.loginUser(user).enqueue(callback);
    }

    public void registerUser(final User user, final Callback<Object> callback){
        mUserService.createUser(user).enqueue(callback);
    }

    public void getAllUsers(final Callback<ArrayList<User>> callback){
        mUserService.listAllUsers().enqueue(callback);
    }


    //TODO: treasure service

    public  void createdTreasureClaim(final TreasureClaim treasureClaim, final Callback<String>callback){
        mTreasureService.createdTreasureClaim(treasureClaim).enqueue(callback);
    }


    public void createTreasure(Treasure treasure, Callback<Treasure> treasureCallback) {
        mTreasureService.createTreasure(treasure).enqueue(treasureCallback);
    }
}
