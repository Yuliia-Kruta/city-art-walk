package com.example.csc202assignment

import android.content.Context
import androidx.room.Room
import com.example.csc202assignment.database.ArtworkDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.util.UUID

private const val DATABASE_NAME = "artwork-database"

class ArtworkRepository private constructor(
    context: Context
){
    private val database: ArtworkDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ArtworkDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    //fun getArtworks(): Flow<List<Artwork>> = database.artworkDao().getArtworks()

    fun getArtworks(): Flow<List<Artwork>> = flow {
        val dummyData = listOf(
            Artwork(
                id = UUID.randomUUID(),
                title = "Starry Night",
                date = Date(),
                address = "123 Art Street, Museum District"
            ),
            Artwork(
                id = UUID.randomUUID(),
                title = "Mona Lisa",
                date = Date(),
                address = "456 Louvre Street, Paris"
            ),
            Artwork(
                id = UUID.randomUUID(),
                title = "The Persistence of Memory",
                date = Date(),
                address = "789 Surrealist Ave, New York"
            ),
            Artwork(
                id = UUID.randomUUID(),
                title = "The Scream",
                date = Date(),
                address = "101 Edvard Munch Rd, Oslo"
            )
        )
        emit(dummyData)
    }

    suspend fun addArtwork(artwork: Artwork) {
        database.artworkDao().addArtwork(artwork)
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