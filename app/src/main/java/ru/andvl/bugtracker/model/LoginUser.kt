package ru.andvl.bugtracker.model

import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json

@Immutable
data class LoginUser(
    @field:Json(name = "email")
    val login: String,
    val password: String = "",
    @field:Json(name = "name")
    val nickname: String = ""
)
