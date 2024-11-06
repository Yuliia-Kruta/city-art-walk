package com.example.csc202assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.csc202assignment.Artwork

@Database(entities = [Artwork::class], version = 2)
@TypeConverters(ArtworkTypeConverters::class)
abstract class ArtworkDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao
}

val migration_1_2 = object : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Artwork ADD COLUMN photoFileName TEXT"
        )
    }
}