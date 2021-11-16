package com.example.mappoliyline.classesss

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mappoliyline.roomm.db.DataBaseHelper
import com.example.mappoliyline.roomm.entity.MyMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class LocationReceiver : BroadcastReceiver() {

    private var locationPermissionGranted = true
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    private lateinit var dataBaseHelper: DataBaseHelper

    override fun onReceive(context: Context, intent: Intent) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        dataBaseHelper = DataBaseHelper.getInstance(context)
        getDeviceLocation(context)

    }


    fun getDeviceLocation(context: Context) {

        try {
            if (locationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener {
                    if (it.isSuccessful) {
                        lastKnownLocation = it.result
                        if (lastKnownLocation != null) {
                            LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            dataBaseHelper.helper().addMap(MyMap(leng = lastKnownLocation?.latitude!!, height = lastKnownLocation?.longitude!!))

                            Toast.makeText(context, "salom", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        } catch (e: Exception) {

        }

    }
}