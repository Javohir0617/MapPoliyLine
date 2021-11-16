package com.example.mappoliyline

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Database
import com.example.mappoliyline.classesss.LocationReceiver
import com.example.mappoliyline.databinding.ActivityEnterBinding
import com.example.mappoliyline.roomm.db.DataBaseHelper
import com.example.mappoliyline.roomm.entity.MyMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class EnterActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityEnterBinding
    private var locationPermissionGranted = false
    private lateinit var dataBaseHelper: DataBaseHelper
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocationPermission()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        dataBaseHelper = DataBaseHelper.getInstance(this)


        binding.apply {

            getDeviceLocation()
            if (locationPermissionGranted) {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this@EnterActivity, LocationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this@EnterActivity, 1, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    15 * 60 * 1000,
                    pendingIntent
                )

            }
            cardMap.setOnClickListener {
                val intent = Intent(this@EnterActivity, MapsActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
    }


    fun getDeviceLocation() {

        try {
            if (locationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
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
                            binding.lengthTxt.text = lastKnownLocation?.latitude.toString()
                            binding.heightTxt.text = lastKnownLocation?.longitude.toString()
                        }
                    }

                }
            }
        } catch (e: Exception) {

        }
    }

}