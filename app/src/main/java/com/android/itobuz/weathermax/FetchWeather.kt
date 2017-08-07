package com.android.itobuz.weathermax

import java.net.URL
import java.util.*


/**
 * Created by Debasis on 27/07/17.
 */
class FetchWeather{

    private val OPEN_WEATHER_MAP_API = "2d83a313b5a01d6e218bef418ad6599f"
    private val OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?q=%1s&units=metric&APPID=%2s"//lat=%s&lon=%s

    fun getWeatherJSON(city: String): URL? {   //String lat, String lon
        val url = URL(String.format(OPEN_WEATHER_MAP_URL, city,OPEN_WEATHER_MAP_API))//lat, lon
        return url
    }

    fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long): String {
        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
            val currentTime = Date().time
            if (currentTime in sunrise..(sunset - 1)) {
                icon = "&#xf00d;"
            } else {
                icon = "&#xf02e;"
            }
        } else {
            when (id) {
            2 -> icon = "&#xf01e;" //wi-thunderstorm
            3 -> icon = "&#xf01c;" //wi-sprinkle
            7 -> icon = "&#xf014;" //wi-fog
            8 -> icon = "&#xf013;" //wi-cloudy
            6 -> icon = "&#xf01b;" //wi-snow
            5 -> icon = "&#xf019;" //wi-rain
            }
        }
        return icon
    }


}

