package com.threess.summership.treasurehunt.service;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TreasuresRetrofitService {
    /*String BASE_URL = "http://5.254.125.248:3000/";

    //GET All existing treasures: /treasures
    @GET("/treasures")
    Call<ArrayList<Treasure>> allExistingTreasureList ();

    //TODO GET All treasures created or claimed by one user: /treasures/:userId

    @GET("/treasures/{username}")
    Call<ArrayList<Treasure>> claimedTreasureByUser (@Path("username") String userName); //who is the user?


    //TODO POST Create a treasure: /treasures/create
    /*
    @POST("/treasures/create")
    Call<Treasure> createdTreasure (@Body Treasure treasure);
    */

}
