package com.android.itobuz.weathermax

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

/**
 * Created by debasis on 29/07/17.
 */
class placeIdTask internal constructor(asyncResponse: AsyncResponse?) : AsyncTask<String, Void, JSONObject>() {

    internal var delegate: AsyncResponse? = null//Call back interface
    var fetchWeather = FetchWeather()

    init {
        delegate = asyncResponse as AsyncResponse
    }

    override fun doInBackground(vararg params: String): JSONObject {

        var jsonWeather: JSONObject? = null
        try {
            jsonWeather = fetchWeather.getWeatherJSON(params[0]) //params[0], params[1]
        } catch (e: Exception) {
            Log.d("Error", "Cannot process JSON results", e)
        }
        return jsonWeather as JSONObject
    }


    override fun onPostExecute(json: JSONObject?) {
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

                delegate!!.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText, "" + json.getJSONObject("sys").getLong("sunrise") * 1000)

            }
        } catch (e: JSONException) {
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
        }


    }
}
