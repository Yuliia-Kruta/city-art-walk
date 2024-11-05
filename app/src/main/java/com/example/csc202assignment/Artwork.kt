package com.example.csc202assignment

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Artwork(
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
    val address: String
)