package ru.andvl.bugtracker.utils

import androidx.room.TypeConverter
import ru.andvl.bugtracker.model.Label

class LabelConverter {
    @TypeConverter
    fun toLabel(value: String) = enumValueOf<Label>(value)

    @TypeConverter
    fun fromLabel(value: Label) = value.name
}
