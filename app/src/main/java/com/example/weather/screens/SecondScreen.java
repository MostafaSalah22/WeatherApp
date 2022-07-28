package com.example.weather.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.retrofit.ApiClient;
import com.example.weather.retrofit.ApiInterface;
import com.example.weather.retrofit.CityModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondScreen extends AppCompatActivity {

    ImageView gifTemp , gifWeather , gifHumidity , gifWind , loading;
    private static final String API_KEY = "c6105274798eba971ecc93a1a4983f91" ;
    TextView secondTemp, secondCity, tempTV, weatherTV, humidityTV, windTV;
    CardView cardView;
    String city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        // inflate the views
        inflateViews();

        // make all icons animate (gif images)
        glide();

        Intent data = getIntent();
        city = data.getExtras().getString("city");

        myTask task = new myTask();
        task.execute();

    }





    private void glide(){
        Glide.with(this).load(R.drawable.tempgif).into(gifTemp);
        Glide.with(this).load(R.drawable.cloudgif).into(gifWeather);
        Glide.with(this).load(R.drawable.leafs).into(gifHumidity);
        Glide.with(this).load(R.drawable.wind).into(gifWind);
    }


    private void inflateViews(){
        gifTemp = findViewById(R.id.gifTemp);
        gifWeather = findViewById(R.id.gifWeather);
        gifHumidity = findViewById(R.id.gifHumidity);
        gifWind = findViewById(R.id.gifWind);
        secondTemp = findViewById(R.id.secondTemp);
        secondCity = findViewById(R.id.secondCity);
        tempTV = findViewById(R.id.tempTV);
        humidityTV = findViewById(R.id.humidityTV);
        windTV = findViewById(R.id.windTV);
        weatherTV = findViewById(R.id.weatherTV);
        loading = findViewById(R.id.loading);
        cardView = findViewById(R.id.cardView);
    }


    class myTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        loading.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        secondCity.setVisibility(View.GONE);
        secondTemp.setVisibility(View.GONE);

    }


        @Override
        protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        loading.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        secondCity.setVisibility(View.VISIBLE);
        secondTemp.setVisibility(View.VISIBLE);
    }


        @Override
        protected Void doInBackground(String... strings) {

            Response<CityModel> response = null;
            try {
                 response =ApiClient.getINSTANCE().getCity(city).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            secondCity.setText(city);
            secondTemp.setText(response.body().getMain().getTemp() + "°");
            tempTV.setText(response.body().getMain().getTemp() + "°");
            humidityTV.setText(response.body().getMain().getHumidity() + "%");
            windTV.setText(response.body().getWind().getSpeed());
            weatherTV.setText(response.body().getWeather().get(0).getMain());


            return null;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SecondScreen.this,FirstScreen.class);
        startActivity(intent);
    }

}