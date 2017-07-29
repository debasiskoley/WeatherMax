package com.android.itobuz.weathermax

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * Created by macmini3 on 27/07/17.
 */
class FetchWeather{

    private val OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"//lat=%s&lon=%s
    private val OPEN_WEATHER_MAP_API = "2d83a313b5a01d6e218bef418ad6599f"

    fun getWeatherJSON(city: String): JSONObject? {   //String lat, String lon
        try {
            val url = URL(String.format(OPEN_WEATHER_MAP_URL, city))//lat, lon
            val connection = url.openConnection() as HttpURLConnection

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API)

            val reader = BufferedReader(
                    InputStreamReader(connection.inputStream))

            val json = StringBuilder(1024)
            var tmp = ""
            tmp = reader.readLine()
            while (tmp != null) json.append(tmp).append("\n")
            reader.close()

            val data = JSONObject(json.toString())

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                return null
            }

            return data
        } catch (e: Exception) {
            return null
        }

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
                2 -> icon = "&#xf01e;"
                3 -> icon = "&#xf01c;"
                7 -> icon = "&#xf014;"
                8 -> icon = "&#xf013;"
                6 -> icon = "&#xf01b;"
                5 -> icon = "&#xf019;"
            }
        }
        return icon
    }


}

