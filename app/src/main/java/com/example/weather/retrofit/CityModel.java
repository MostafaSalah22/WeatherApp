package com.example.weather.retrofit;

import java.util.ArrayList;

public class CityModel {

    private  Main main;
    private ArrayList<Weather> weather;
    private Wind wind;


    public Main getMain() {
        return main;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public class Main{
        String temp;
        String humidity;

        public String getTemp() {
            return temp;
        }

        public String getHumidity() {
            return humidity;
        }
    }

    public class Weather{
        String main;

        public String getMain() {
            return main;
        }
    }

    public class Wind{
        String speed;

        public String getSpeed() {
            return speed;
        }
    }
}
