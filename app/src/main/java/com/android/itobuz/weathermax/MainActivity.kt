package com.android.itobuz.weathermax

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var cityName: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       cityName = findViewById(R.id.city_name)

        submit_city.setOnClickListener {

//            Log.i("cityName", "hghfgf"+ cityName?.text.toString())

            if (cityName?.text.toString().isEmpty()){
                Toast.makeText(this, R.string.city_name_error_text, Toast.LENGTH_SHORT).show()
            } else {
                toWeather()
            }

        }

    }


    fun toWeather() {
        val i = Intent(this@MainActivity, WeatherActivity::class.java)
        startActivity(i)
    }
}

