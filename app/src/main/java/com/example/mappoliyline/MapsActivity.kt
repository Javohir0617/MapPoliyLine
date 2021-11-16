package com.example.mappoliyline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mappoliyline.databinding.ActivityMapsBinding
import com.example.mappoliyline.roomm.db.DataBaseHelper
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var databaseHelper: DataBaseHelper
    private lateinit var list: ArrayList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DataBaseHelper.getInstance(this)
        list = ArrayList()
        setLatLng()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(list)
        )

        val sydney = LatLng(list[0].latitude, list[0].longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title("You"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

        mMap.setOnPolylineClickListener(this)
    }

    fun setLatLng() {
        list.clear()
        val allMaps = databaseHelper.helper().getAllMaps()
        for (allMap in allMaps) {
            list.add(LatLng(allMap.leng, allMap.height))
        }
    }

    override fun onPolylineClick(p0: Polyline) {

    }
}