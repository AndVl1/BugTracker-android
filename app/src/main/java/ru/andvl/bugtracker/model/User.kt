package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("userId")
    @PrimaryKey
    val userId: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
)
