package com.android.itobuz.weathermax

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : PermissionsActivity() {

    private var cityName: EditText? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    //@SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        my_location.setOnClickListener {
                requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION, object : PermissionCallBack {
                    override fun permissionGranted() {
                        super.permissionGranted()
//                        Log.v("Location Permission", "Granted")

                    }

                    override fun permissionDenied() {
                        super.permissionDenied()
                        Log.v("Location Permission", "Denied")
                    }
                })

        }

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
