package com.threess.summership.treasurehunt.service;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TreasuresRetrofitService {

    //GET All existing treasures: /treasures
    @GET("/treasures")
    Call<ArrayList<Treasure>> allExistingTreasureList ();

    //TODO GET All treasures created or claimed by one user: /treasures/:username
    @GET("/treasures/{username}")
    Call<ArrayList<Treasure>> claimedTreasureByUser (@Query("username") String userName);

    //TODO POST Create a treasure: /treasures/create
    @POST("/treasures/create")
    Call<Treasure> createTreasure(@Body Treasure treasure);

    //TODO POST a treasure what was founded
    @POST("/treasures/claim")
    Call<String> createdTreasureClaim (@Body TreasureClaim treasureClaim);
/*
    @POST("/treasures/update/{passcode}/{username}")///treasures/update/:passcode/:username
    Call<Treasure> createTreasure(@("passcode") String passcode, @Query("username") String username);
*/
}
