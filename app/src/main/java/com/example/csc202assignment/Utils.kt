package com.example.csc202assignment

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "MMM d, yyyy"

object Utils {
    fun formatArtworkDate(date: Date): String {
        val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormatter.format(date)
    }
}