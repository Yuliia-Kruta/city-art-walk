package com.example.csc202assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.csc202assignment.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdate

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitude = intent.getDoubleExtra("LATITUDE", -24.7174)
        val longitude = intent.getDoubleExtra("LONGITUDE", 153.062151)
        updateUI(latitude, longitude)
    }

    private fun updateUI(latitude: Double, longitude: Double) {
        mMap.clear()
        val mypoint = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(mypoint).title("Artwork location"))

        val zoomLevel: Float = 10f
        val update: CameraUpdate = CameraUpdateFactory.newLatLngZoom(mypoint, zoomLevel)

        mMap.animateCamera(update)
    }
}