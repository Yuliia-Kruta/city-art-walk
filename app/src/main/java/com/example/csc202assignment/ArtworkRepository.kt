package com.example.csc202assignment

import android.content.Context
import androidx.room.Room
import com.example.csc202assignment.database.ArtworkDatabase
import com.example.csc202assignment.database.migration_1_2
import com.example.csc202assignment.database.migration_2_3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val DATABASE_NAME = "artwork-database"

class ArtworkRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: ArtworkDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ArtworkDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(migration_1_2, migration_2_3)
        .build()

    fun getArtworks(): Flow<List<Artwork>> = database.artworkDao().getArtworks()

    suspend fun getArtwork(id: UUID): Artwork = database.artworkDao().getArtwork(id)

    fun updateArtwork(artwork: Artwork) {
        coroutineScope.launch {
            database.artworkDao().updateArtwork(artwork)
        }
    }

    suspend fun addArtwork(artwork: Artwork) {
        database.artworkDao().addArtwork(artwork)
    }

    suspend fun deleteArtwork(artwork: Artwork) {
        coroutineScope.launch {
            database.artworkDao().deleteArtwork(artwork)
        }
    }

    companion object {
        private var INSTANCE: ArtworkRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ArtworkRepository(context)
            }
        }

        fun get(): ArtworkRepository {
            return INSTANCE
                ?: throw IllegalStateException("ArtworkRepository must be initialized")
        }
    }

}