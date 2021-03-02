package ru.andvl.bugtracker.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUser(
    val login: String,
    val password: String,
)
