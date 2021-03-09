package ru.andvl.bugtracker.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController

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

@Composable
fun BottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController = navController)
        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo = navController.graph.startDestination
//                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.contentDescription
                    ) },
                label = { Text(stringResource(id = screen.resourceId)) },
                alwaysShowLabel = false,

            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Projects,
        BottomNavigationScreens.Tasks,
        BottomNavigationScreens.Profile
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        }
    ) {
        BottomNavBarConfiguration(navController = navController)
    }
}