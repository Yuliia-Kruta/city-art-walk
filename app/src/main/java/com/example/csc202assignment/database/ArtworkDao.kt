package com.example.csc202assignment.database

import androidx.room.Dao
import androidx.room.Query
import com.example.csc202assignment.Artwork
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM artwork")
    fun getArtworks(): Flow<List<Artwork>>
}