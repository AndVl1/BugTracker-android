package ru.andvl.bugtracker.utils

import androidx.room.TypeConverter
import ru.andvl.bugtracker.model.Status

class StatusConverter {
    @TypeConverter
    fun toStatus(value: Int) = when(value) {
        1 -> Status.NEW
        2 -> Status.IN_PROGRESS
        3 -> Status.REVIEW
        4 -> Status.TESTING
        5 -> Status.READY
        6 -> Status.CLOSED
        else -> Status.ERROR
    }

    @TypeConverter
    fun fromStatus(value: Status) = value.name
}
