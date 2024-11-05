package com.example.csc202assignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.csc202assignment.Artwork
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM artwork")
    fun getArtworks(): Flow<List<Artwork>>

    @Query("SELECT * FROM artwork WHERE id=(:id)")
    suspend fun getArtwork(id: UUID): Artwork

    @Update
    suspend fun updateArtwork(artwork: Artwork)

    @Insert
    suspend fun addArtwork(artwork: Artwork)
}