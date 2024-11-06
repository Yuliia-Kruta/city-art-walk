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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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