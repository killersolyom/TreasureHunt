package com.threess.summership.treasurehunt.service;

import com.threess.summership.treasurehunt.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRetrofitService {

    @POST("/users/register")
    Call<Object> createUser(@Body User user);

    @POST("/users/login")
    Call<Object> loginUser(@Body User user);

    @GET("/users")
    Call <ArrayList<User>> listAllUsers ();



}
