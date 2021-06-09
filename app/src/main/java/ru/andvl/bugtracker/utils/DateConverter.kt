package ru.andvl.bugtracker.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}

fun Long.convertToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US)
    return format.format(date)
}
