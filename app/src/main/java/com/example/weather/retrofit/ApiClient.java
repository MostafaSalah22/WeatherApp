package com.example.weather.retrofit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.openweathermap.org/" ;
    private static final String API_KEY = "c6105274798eba971ecc93a1a4983f91" ;
    private ApiInterface apiInterface ;
    private static ApiClient INSTANCE;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


         apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTANCE() {
        if(null == INSTANCE){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public Call<CityModel> getCity(String city){
        return apiInterface.getCity(city,API_KEY,"metric");
    }
}
