package ru.andvl.bugtracker.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class LoginUser(
    @SerializedName("email")
    val login: String,
    val password: String,
)
