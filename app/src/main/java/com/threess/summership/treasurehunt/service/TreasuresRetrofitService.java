package com.threess.summership.treasurehunt.service;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.TreasureClaim;
import com.threess.summership.treasurehunt.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TreasuresRetrofitService {

    //GET All existing treasures: /treasures
    @GET("/treasures")
    Call<ArrayList<Treasure>> allExistingTreasureList ();

    //TODO GET All treasures created or claimed by one user: /treasures/:userId

    @GET("/treasures/{username}")
    Call<ArrayList<Treasure>> claimedTreasureByUser (@Path("username") String userName); //who is the user?

    //TODO POST Create a treasure: /treasures/create
    @POST("/treasures/create")
    Call<Treasure> createTreasure(@Body Treasure treasure);

    //TODO POST a tresure what was founded
    @POST("/treasures/claim")
    Call<String> createdTreasureClaim (@Body TreasureClaim treasureClaim);



}
