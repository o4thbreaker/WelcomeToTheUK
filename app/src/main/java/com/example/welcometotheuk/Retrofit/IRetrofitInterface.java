package com.example.welcometotheuk.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRetrofitInterface {
    @GET("v6/834a53b4f5e90c394972dc8e/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
