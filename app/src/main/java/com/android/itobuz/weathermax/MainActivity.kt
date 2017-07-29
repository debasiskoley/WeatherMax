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
        val bundle = Bundle()

        submit_city.setOnClickListener {

            if (cityName?.text.toString().isEmpty()){
                Toast.makeText(this, R.string.city_name_error_text, Toast.LENGTH_SHORT).show()
            } else {
                bundle.putString("CITY_NAME", cityName?.text.toString())
                toWeather(bundle)
            }

        }

    }


    fun toWeather(bundle: Bundle) {
        val i = Intent(this@MainActivity, WeatherActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}

