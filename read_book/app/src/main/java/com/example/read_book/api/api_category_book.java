package com.example.read_book.api;

import com.example.read_book.model.Category_book;
import com.example.read_book.model.User;
import com.example.read_book.setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface api_category_book {
    setting s = new setting();
    Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    api_category_book api_cate_bo = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_category_book.class);

    @GET("api/category_book/read_all")
    Call<List<Category_book>> show_all_category_book();

    @GET("api/category_book/read_by_id_book/{id_book}")
    Call<List<Category_book>> category_book_readByID_book(
            @Path("id_book") int id_book
    );

    @POST("/api/category_book/create")
    @FormUrlEncoded
    Call<Category_book> category_book_create(
            @Field("id_category") int id_category,
            @Field("id_book") int id_book);


    @DELETE("/api/category_book/delete/{id_category}/{id_book}")
    Call<ArrayList> delete_category_book(
        @Path("id_category") int id_category,
        @Path("id_book") int id_book
    );

}
