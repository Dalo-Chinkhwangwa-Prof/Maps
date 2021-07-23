package com.dynamicdevz.mymapapplication

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager

    val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                registerLocation()
            } else {
                ///Show location needed dialog
                Log.d("TAG_X", "Disabled....")
            }
        }

    override fun onResume() {
        super.onResume()
        locationPermissionRequest.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun registerLocation() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            this
        )
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
    }
    private lateinit var fragment: MapsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapsFragment
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    override fun onLocationChanged(p0: Location) {

        Log.d("TAG_X", "${p0.latitude}, ${p0.longitude}")
        fragment.showLocation(p0)

    }


}