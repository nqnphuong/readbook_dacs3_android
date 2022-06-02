package com.example.read_book.api;

import com.example.read_book.model.Category;
import com.example.read_book.model.User;
import com.example.read_book.setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface api_category {
    setting s = new setting();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    api_category api_ca = new Retrofit.Builder()
            .baseUrl(s.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api_category.class);

    @GET("api/category/read_all")
    Call<List<Category>> show_all_category();
}
