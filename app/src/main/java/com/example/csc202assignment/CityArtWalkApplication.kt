package com.example.csc202assignment

import android.app.Application

class CityArtWalkApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ArtworkRepository.initialize(this)
    }
}