package com.android.itobuz.weathermax

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity(), AsyncResponse {

    internal var weatherFont: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val fontPath = "fonts/weather.ttf"
        // getting custom font
        val tf = Typeface.createFromAsset(assets, fontPath)

        // Applying font
        current_temperature_field.typeface = tf

        val bundle = intent.extras
        val cityName = bundle!!.getString("CITY_NAME")

        GetWeatherTask(this).execute(cityName)
    }

    override fun processFinish(weather_city : String, weather_description: String, weather_temperature: String, weather_humidity:String, weather_pressure: String , weather_updatedOn:String, weather_iconText:String, sun_rise:String) {
          city_field.text = weather_city
          updated_field.text = weather_updatedOn
          details_field.text = weather_description
          current_temperature_field.text = weather_temperature
          humidity_field.text = weather_humidity
          pressure_field.text = weather_pressure
          weather_icon.text = weather_iconText
    }

}
