package com.example.read_book.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.read_book.setting;
import com.example.read_book.model.User;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface api_user {
    setting s = new setting();
    Gson gson = new GsonBuilder()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    api_user api_us = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_user.class);

    @GET("api/user/read_all")
    Call<List<User>> show_all_user();

    @POST("api/user/create")
    @FormUrlEncoded
    Call<User> create_user(
            @Field("userEmail") String userEmail,
            @Field("userPassword") String userPassword,
            @Field("userFirstname") String userFirstname,
            @Field("userCity") String userCity,
            @Field("userAge") int userAge,
            @Field("userDayofbirth") String userDayofbirth,
            @Field("userImage1") String userImage1,
            @Field("userImage2") String userImage2,
            @Field("userAndress") String userAndress,
            @Field("userPhone") String userPhone,
            @Field("userDescription") String userDescription);


    @GET("/api/user/read_by_id/{id_user}")
    Call<List<User>> user_readByID(
            @Path("id_user") int id_user
    );


    @PUT("/api/user/update/{id_user}")
    @FormUrlEncoded
    Call<List<User>> update_user(
            @Path("id_user") int  id_user,
            @Field("userEmail") String  userEmail,
            @Field("userPassword") String  userPassword,
            @Field("userFirstname") String  userFirstname,
            @Field("userCity") String  userCity,
            @Field("userAge") int  userAge,
            @Field("userDayofbirth") String  userDayofbirth,
            @Field("userImage1") String  userImage1,
            @Field("userImage2") String  userImage2,
            @Field("userAndress") String  userAndress,
            @Field("userPhone") String  userPhone,
            @Field("userDescription") String  userDescription);

}
