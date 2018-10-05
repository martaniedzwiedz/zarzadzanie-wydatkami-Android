package com.example.marta.testobserverv2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Builder {
    private Retrofit.Builder builder = new Retrofit.Builder()
            //.baseUrl("http://10.0.2.2:8000/")
            .baseUrl("https://pss-app.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    private Retrofit retrofit = builder.build();

    UserClient userClient = retrofit.create(UserClient.class);
}