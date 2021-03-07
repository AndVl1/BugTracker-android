package ru.andvl.bugtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavBarConfiguration(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.Projects.route,
    ) {
        composable(BottomNavigationScreens.Projects.route) {}
        composable(BottomNavigationScreens.Profile.route) {}
        composable(BottomNavigationScreens.Tasks.route) {}
    }
}