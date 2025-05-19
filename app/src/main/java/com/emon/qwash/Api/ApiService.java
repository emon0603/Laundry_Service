package com.emon.qwash.Api;


import com.emon.qwash.ModelClass.ApiResponse;
import com.emon.qwash.ModelClass.ProfileResponse;
import com.emon.qwash.ModelClass.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @Multipart
    @POST("signup.php")
    Call<RegisterResponse> registerUser(
            @Header("Authorization") String secretKey,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("number") RequestBody number,
            @Part MultipartBody.Part image
    );


    @Multipart
    @POST("singin.php")
    Call<com.emon.qwash.ModelClass.LoginResponse> loginUser(
            @Header("Authorization") String authKey,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );




    //---------------------- Data Fetch ----------------------------


    @FormUrlEncoded
    @Headers("Authorization: your_secret_key_123") // Replace with actual SECRET_KEY
    @POST("Profile/get/get_profile.php")
    Call<ProfileResponse> getProfile(
            @Field("email") String email
    );




    //--------------- Update info -------------


    // same as PHP SECRET_KEY
    @Headers({"Authorization: your_secret_key_123",
            "Content-Type: application/x-www-form-urlencoded"}) // same as PHP SECRET_KEY
    @FormUrlEncoded
    @PUT("Profile/update/profile_update.php")
    Call<ApiResponse> updateProfile(
            @Field("email") String email,
            @Field("name") String name,
            @Field("number") String number
    );












}
