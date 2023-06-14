package com.weatherapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import com.weatherapplication.R
import com.weatherapplication.model.data.CityCoordinates
import com.weatherapplication.util.PERMISSION_ID
import com.weatherapplication.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: WeatherViewModel by viewModels()
    private lateinit var fusedLocationProvideClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        fusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState == null) {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProvideClient.lastLocation.addOnCompleteListener(OnCompleteListener<Location?> { task ->
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                        Toast.makeText(this, "Using device location.", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.updateCurrentLonLat(
                            CityCoordinates(
                                location.longitude.toString(),
                                location.latitude.toString()
                            )
                        )
                        getWeatherDataAndMoveToDetailsScreen()
                        requestNewLocationData()
                    }
                })
            } else {
                Toast.makeText(
                    this,
                    "Please turn on your location...",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {}
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setIntervalMillis(500)
            .setWaitForAccurateLocation(false)
            .setMaxUpdateDelayMillis(1000)
            .build()

        fusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProvideClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLastLocation()
                PackageManager.PERMISSION_DENIED -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.to_citySelectorFragment)
                    Toast.makeText(
                        this,
                        "You can enter city manually...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getWeatherDataAndMoveToDetailsScreen() {
        viewModel.updateWeatherData()
        //findNavController(R.id.nav_host_fragment).navigate(R.id.to_detailedWeatherFragment)
    }

    override fun onBackPressed() {
        // Overriding this since we use search to navigate back to city selector for ease of use
        this.finish()
    }
}
