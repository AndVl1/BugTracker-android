package ru.andvl.bugtracker.utils

import androidx.room.TypeConverter
import ru.andvl.bugtracker.model.AssigneeID

class AssigneeConverter {
    @TypeConverter
    fun toNullableInt(value: AssigneeID): Int =
        if (value.valid)
            value.value
        else 0

    @TypeConverter
    fun toAssigneeId(value: Int): AssigneeID =
        if (value == 0)
            AssigneeID(0, false)
        else AssigneeID(value, true)
}


