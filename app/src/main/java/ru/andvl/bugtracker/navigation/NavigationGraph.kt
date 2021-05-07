package ru.andvl.bugtracker.navigation

import androidx.navigation.NavController

object Destinations {
    const val Splash = "splash"
    const val Login = "login"
    const val CheckEmail = "register_email"
    const val NicknamePasswordInput = "register_password"

    const val MainScreenNavigation = "main_screen"

    const val AddProject = "add_project"
}

class Actions(navController: NavController) {
    val navigateUp: () -> Unit = {
        navController.popBackStack()
    }
}
