package com.example.read_book.api;

import com.example.read_book.model.Book;
import com.example.read_book.model.Chapter;
import com.example.read_book.model.User;
import com.example.read_book.setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface api_chapter {

    setting s = new setting();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    api_chapter api_ch = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_chapter.class);

    @GET("api/chapter/read_all")
    Call<List<Chapter>> show_all_chapter();

    @GET("/api/chapter/read_by_id_book/{id_book}")
    Call<List<Chapter>> read_by_id_book(@Path("id_book") Integer id_book);

    @GET("/api/chapter/read_by_id/{id_chapter}")
    Call<List<Chapter>> read_by_id(@Path("id_chapter") Integer id_chapter);

    @POST("api/chapter/create")
    @FormUrlEncoded
    Call<Chapter> create_chapter(
            @Field("id_book") int id_book,
            @Field("chapterContent") String chapterContent,
            @Field("chapterNumberLove") int chapterNumberLove,
            @Field("chapterTitle") String chapterTitle);

    @DELETE("api/chapter/delete/{id_chapter}")
    Call<List<Chapter>> delete_chapter(
            @Path("id_chapter") int id_chapter
    );

    @PUT("api/chapter/update/{id_chapter}")
    @FormUrlEncoded
    Call<List<Chapter>> update_chapter(
            @Path("id_chapter") int id_chapter,
            @Field("chapterContent") String chapterContent,
            @Field("chapterTitle") String chapterTitle);

}
