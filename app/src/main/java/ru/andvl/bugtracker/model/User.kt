package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @field:Json(name = "userId")
    @PrimaryKey
    val userId: Int,
    @field:Json(name = "login")
    val login: String,
    @field:Json(name = "name")
    val name: String,
)
