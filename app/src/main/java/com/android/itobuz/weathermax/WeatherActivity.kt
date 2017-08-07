package com.android.itobuz.weathermax

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity(), AsyncResponse {

    var weatherFont: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val bundle = intent.extras
        val cityName = bundle!!.getString("CITY_NAME")

        GetWeatherTask(this).execute(cityName)

        val fontPath = "fonts/weathericons-regular.ttf"
        //getting custom font
        weatherFont = Typeface.createFromAsset(applicationContext.assets, fontPath)
        // Applying font
        weather_icon.typeface = weatherFont
    }

    override fun processFinish(weather_city : String, weather_description: String, weather_temperature: String, weather_humidity:String, weather_pressure: String , weather_updatedOn:String, weather_iconText:String, sun_rise:String) {
          city_field.text = weather_city
          updated_field.text = weather_updatedOn
          details_field.text = weather_description
          current_temperature_field.text = weather_temperature
          humidity_field.text = weather_humidity
          pressure_field.text = weather_pressure

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                weather_icon.text = Html.fromHtml(weather_iconText, 1)
            }else{
                weather_icon.text = Html.fromHtml(weather_iconText)
            }
//          weather_icon.setIconResource(weather_iconText)

    }

}
