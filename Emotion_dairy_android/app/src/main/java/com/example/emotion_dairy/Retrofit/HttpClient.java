package com.example.emotion_dairy.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static Retrofit retrofit;


    public static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder().setLenient().create();
        if(retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl("http://10.100.102.31:8000/");
            builder.addConverterFactory(GsonConverterFactory.create(gson));

            retrofit = builder.build();
        }

        return retrofit;
    }
}
