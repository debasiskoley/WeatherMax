package com.android.itobuz.weathermax

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity() {

    internal var weatherFont: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherFont = Typeface.createFromAsset(applicationContext.assets, "fonts/weather.ttf")

        val bundle = intent.extras
        val cityName = bundle!!.getString("CITY_NAME")

        val asyncTask = placeIdTask(object : AsyncResponse {

            override fun processFinish(weather_city: String, weather_description: String, weather_temperature: String, weather_humidity: String, weather_pressure: String, weather_updatedOn: String, weather_iconText: String, sun_rise: String) {

                city_field.text = weather_city
                updated_field.text = weather_updatedOn
                details_field.text = weather_description
                current_temperature_field.text = weather_temperature
                humidity_field.text = "Humidity: " + weather_humidity
                pressure_field.text = "Pressure: " + weather_pressure

                if (Build.VERSION.SDK_INT >= 24) {
                    weather_icon.text = Html.fromHtml(weather_iconText, 16)
                } else {
                    weather_icon.text = Html.fromHtml(weather_iconText)
                }


            }
        })
        asyncTask.execute(cityName) //  asyncTask.execute("Latitude", "Longitude")


    }

}
