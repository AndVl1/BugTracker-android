package ru.andvl.bugtracker.navigation

import androidx.navigation.NavController

object Destinations {
    const val Login = "login"
    const val CheckEmail = "register_email"
    const val NicknamePasswordInput = "register_password"
}

class Actions(navController: NavController) {
    val navigateUp: () -> Unit = {
        navController.popBackStack()
    }
}
