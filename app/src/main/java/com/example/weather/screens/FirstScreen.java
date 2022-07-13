package com.example.weather.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.retrofit.ApiInterface;
import com.example.weather.retrofit.CityModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstScreen extends AppCompatActivity {

    ImageView imageWeather;
    EditText citySearch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tempDegree , firstCity , firstWeather;
    private static final String API_KEY = "c6105274798eba971ecc93a1a4983f91" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // inflate the views
        inflateViews();

        //gif photo
        Glide.with(this).load(R.drawable.weather).into(imageWeather);

        // get data from the file of shared preference and set the data into card views
        getDataSharedPref();



        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        findViewById(R.id.iconSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = citySearch.getText().toString().trim() ;

                Call<CityModel> call = apiInterface.getCity(city,API_KEY,"metric");
                call.enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        editor.putString("city",city);
                        editor.putString("temp",response.body().getMain().getTemp());
                        editor.putString("weather",response.body().getWeather().get(0).getMain());
                        editor.commit();

                        Intent intent = new Intent(FirstScreen.this,SecondScreen.class);

                        intent.putExtra("city",city);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        Toast.makeText(FirstScreen.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void inflateViews(){
        imageWeather = findViewById(R.id.imageWeather);
        tempDegree = findViewById(R.id.tempDegree);
        firstCity = findViewById(R.id.firstCity);
        firstWeather = findViewById(R.id.firstWeather);
        citySearch = findViewById(R.id.citySearch);
    }

    private void getDataSharedPref() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        String cityPref = sharedPreferences.getString("city","Null");
        String tempPref = sharedPreferences.getString("temp","Null");
        String weatherPref = sharedPreferences.getString("weather","Null");

        firstCity.setText(cityPref);
        tempDegree.setText(tempPref);
        firstWeather.setText(weatherPref);
    }

}