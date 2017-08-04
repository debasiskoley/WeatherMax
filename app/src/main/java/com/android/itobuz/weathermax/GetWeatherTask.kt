package com.android.itobuz.weathermax

import android.os.AsyncTask
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.text.DateFormat
import java.util.*

/**
 * Created by debasis-mac on 03/08/17.
 */

class GetWeatherTask(var asyncResponse: AsyncResponse) : AsyncTask<String, Void, String>() {

    @Throws(IOException::class)
    private fun downloadData(city: String): String {
        var inputStream: InputStream? = null
        try {
            val url = FetchWeather().getWeatherJSON(city)
            val conn = url?.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()

            inputStream = conn.inputStream
            return inputStream.bufferedReader().use { it.readText() }
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
    }

    override fun doInBackground(vararg params: String): String? {

        try {
            return downloadData(params[0])
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }


    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val json : JSONObject = JSONObject(result)
        val fetchWeather = FetchWeather()

        try {
            if (json != null) {
                val details = json.getJSONArray("weather").getJSONObject(0)
                val main = json.getJSONObject("main")
                val df = DateFormat.getDateTimeInstance()

                val city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country")
                val description = details.getString("description").toUpperCase(Locale.US)
                val temperature = String.format("%.2f", main.getDouble("temp")) + "°"
                val humidity = main.getString("humidity") + "%"
                val pressure = main.getString("pressure") + " hPa"
                val updatedOn = df.format(Date(json.getLong("dt") * 1000))
                val iconText = fetchWeather.setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000)
                val sunrise = ""+json.getJSONObject("sys").getLong("sunrise") * 1000

                //Log.i("Weather-info","$city , $description, $temperature")
                asyncResponse.processFinish(city,description,temperature,humidity,pressure,updatedOn,iconText, sunrise)

            }
        } catch (e: JSONException) {
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
            e.printStackTrace()
        }


    }



}