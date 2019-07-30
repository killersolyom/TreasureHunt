package com.threess.summership.treasurehunt.service;

import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserRetrofitService {
     public static String BASE_URL = "http://5.254.125.248:3000/";

    @POST("/users/register")
    Call<Object> createUser(@Body User user);

    @POST("/users/login")
    Call<Object> loginUser(@Body User user);

    @GET("/users")
    Call <ArrayList<User>> listAllUsers ();

    @POST("/users/update/{username}")
    @FormUrlEncoded
    Call<Object> updateScore(@Path("username") String username, @Field("score") Double score);

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @Multipart
    @POST("/users/update/{username}")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("file") RequestBody requestBody, @Path("username") String username);

}
