package com.example.csc202assignment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private const val DATE_FORMAT = "MMM d, yyyy"

object Utils {
    fun formatArtworkDate(date: Date): String {
        val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormatter.format(date)
    }

    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()


        val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWidth) {
            1
        } else {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            minOf(heightScale, widthScale).roundToInt()
        }


        return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
            inSampleSize = sampleSize
        })
    }
}