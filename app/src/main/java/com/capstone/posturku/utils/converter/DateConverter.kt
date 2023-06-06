package com.capstone.posturku.utils.converter

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        fun convertToEpochMilliseconds(dateTime: Date): Long {
            return dateTime.time
        }

        fun convertToLocalDateTime(epochMilliseconds: Long): Date {
            return Date(epochMilliseconds)
        }

        fun convertToTimeLocal(epochMilliseconds: Long) : String{
            val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            val date = Date(epochMilliseconds)
            val formattedTime = dateFormat.format(date)
            return formattedTime
        }

        fun convertToDateLocal(epochMilliseconds: Long) : String{
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            val date = Date(epochMilliseconds)
            val formattedTime = dateFormat.format(date)
            return formattedTime
        }
    }
}