package com.example.csc202assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.csc202assignment.Artwork

@Database(entities = [Artwork::class], version = 3)
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
val migration_2_3 = object : Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Artwork ADD COLUMN latitude REAL"
        )
        database.execSQL(
            "ALTER TABLE Artwork ADD COLUMN longitude REAL"
        )
    }
}

