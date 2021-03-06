package com.example.read_book.api;

import com.example.read_book.model.Book;
import com.example.read_book.model.Library;
import com.example.read_book.model.User;
import com.example.read_book.setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface api_library {
    setting s = new setting();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    api_library api_li = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_library.class);

    @GET("api/library/read_all")
    Call<List<Library>> show_all_library();

    @POST("api/library/create")
    @FormUrlEncoded
    Call<Library> create_library(
            @Field("id_user") Integer id_user,
            @Field("id_book") Integer id_book);

    @GET("/api/library/read_by_id_user_book/{id_book}/{id_user}")
    Call<List<Library>> library_readByID_iduser(
            @Path("id_book") int  id_book,
            @Path("id_user") int  id_user
    );

    @DELETE("/api/library/delete/{id_library}")
    Call<ArrayList> delete_library(
            @Path("id_library") int id_library
    );
}
