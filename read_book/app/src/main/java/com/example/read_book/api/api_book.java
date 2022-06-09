package com.example.read_book.api;

import com.example.read_book.model.Book;
import com.example.read_book.model.User;
import com.example.read_book.setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface api_book {
    setting s = new setting();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    api_book api_bo = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_book.class);

    @GET("api/book/read_all")
    Call<List<Book>> show_all_book();

    @GET("/api/book/read_by_id/{id_book}")
    Call<List<Book>> read_by_id(@Path("id_book") Integer id_book);

    @Multipart
    @PUT("/api/book/update/{id_book}")
    Call<List<Book>> book_update(
            @Path("id_book") int  id_book,
            @Part("bookName") RequestBody bookName,
            @Part MultipartBody.Part bookImage,
            @Part("bookDescription") RequestBody  bookDescription);

    @GET("/api/book/read_your_library/{id_user}")
    Call<List<Book>> read_your_library(
            @Path("id_user") int  id_user
    );


    @Multipart
    @POST("/api/book/create")
    Call<Book> create_book(
            @Part("bookName") RequestBody bookName,
            @Part MultipartBody.Part bookImage,
            @Part("bookAuthor") int bookAuthor, //id_user
            @Part("bookDescription") RequestBody bookDescription);


    @DELETE("/api/book/delete/{id_book}")
    Call<ArrayList> delete_book(
            @Path("id_book") int id_book
    );
}
