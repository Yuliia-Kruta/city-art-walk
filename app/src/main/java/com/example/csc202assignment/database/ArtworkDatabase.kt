package com.example.csc202assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.csc202assignment.Artwork

@Database(entities = [Artwork::class], version = 1)
@TypeConverters(ArtworkTypeConverters::class)
abstract class ArtworkDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao
}