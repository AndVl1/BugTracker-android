package ru.andvl.bugtracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.presentation.ui.auth.CheckEmailPage
import ru.andvl.bugtracker.presentation.ui.auth.LoginPage
import ru.andvl.bugtracker.presentation.ui.auth.PasswordNicknamePage
import ru.andvl.bugtracker.presentation.ui.auth.RegisterCheckEmail

@Composable
fun BugTrackerApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    NavHost(navController = navController, startDestination = "login") {
        composable(Destinations.Login) {
            LoginPage(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(Destinations.CheckEmail) {
            CheckEmailPage(viewModel = viewModel)
        }
        composable(Destinations.NicknamePasswordInput) {
            PasswordNicknamePage(viewModel = viewModel)
        }
    }
}
