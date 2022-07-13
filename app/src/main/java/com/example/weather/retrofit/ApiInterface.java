package com.example.weather.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("data/2.5/weather")
    public Call<CityModel> getCity(@Query("q") String city,
                                   @Query("appid") String API_key,
                                   @Query("units") String unit);
}
