package com.cursoemprego.ce.cursoemprego.activity.activity.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private Retrofit retrofit;


    public  static Retrofit getRetrofit(){
        return  new Retrofit.Builder()
                .baseUrl("http://localhost/principal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}