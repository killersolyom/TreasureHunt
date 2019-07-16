package com.threess.summership.treasurehunt.logic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.service.TreasuresRetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private static final String TAG = "ApiController";

    public static void getAllTreasures(final ApiCallback<ArrayList<Treasure>> callback){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl( TreasuresRetrofitService.BASE_URL )
                .build();
        TreasuresRetrofitService service = retrofit.create(TreasuresRetrofitService.class);

        service.allExistingTreasureList().enqueue(new Callback<ArrayList<Treasure>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Treasure>> call, @NonNull Response<ArrayList<Treasure>> response) {
                Log.d(TAG, "Response: " + response);

                ArrayList<Treasure> treasures = response.body();
                callback.Call( treasures );

            }
            @Override
            public void onFailure(Call<ArrayList<Treasure>> call, Throwable t) {
                Log.e(TAG, "Failure: ", t);

                callback.Error(-1);
            }
        });

    }


}
