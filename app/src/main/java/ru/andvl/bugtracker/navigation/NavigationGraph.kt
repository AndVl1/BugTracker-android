package ru.andvl.bugtracker.navigation

import androidx.navigation.NavController

object Destinations {
	const val Login = "login"
	const val Register1 = "register_email"
	const val Register2 = "register_password"
}

class Actions(navController: NavController) {
	val navigateUp: () -> Unit = {
		navController.popBackStack()
	}
}