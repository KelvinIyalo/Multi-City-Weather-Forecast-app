package com.kelvin.weatherappkelvin.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.EasyPermissions

object Permissions {

    var currentLocation: Location? = null
    var isLocationAvailable: Boolean? = false

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun hasPermission(context: Context) = EasyPermissions.hasPermissions(context, *permissions)

    fun requestPermission(context: Activity) {
        EasyPermissions.requestPermissions(
            context,
            "This Application will not work without location permissions.",
            Utility.REQUEST_CODE_LOCATION_PERMISSION,
            *permissions
        )
    }

    fun requestLocation(
        context: Context
    ) {
        val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000 // 10 seconds
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currentLocation = location
                    isLocationAvailable = true
                    fusedLocationProvider.removeLocationUpdates(this)
                }
            }
        }

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                permissions,
                Utility.REQUEST_CODE_LOCATION_PERMISSION
            )
            return
        }

        fusedLocationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }
}