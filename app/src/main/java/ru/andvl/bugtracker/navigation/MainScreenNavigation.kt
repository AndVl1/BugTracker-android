package ru.andvl.bugtracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.presentation.ui.issues.IssueList
import ru.andvl.bugtracker.presentation.ui.issues.IssuesViewModel
import ru.andvl.bugtracker.presentation.ui.profile.ProfileScreen
import ru.andvl.bugtracker.presentation.ui.projects.ProjectList

@ExperimentalAnimationApi
@Composable
private fun BottomNavBarConfiguration(
    nestedNavController: NavHostController,
    mainViewModel: MainViewModel,
    issuesViewModel: IssuesViewModel,
    mainNavController: NavHostController,
) {
    NavHost(
        navController = nestedNavController,
        startDestination = BottomNavigationScreens.Projects.route,
    ) {
        composable(BottomNavigationScreens.Projects.route) {
            ProjectList(
                viewModel = mainViewModel,
                navController = mainNavController,
            )
        }
        composable(BottomNavigationScreens.Profile.route) {
            ProfileScreen(viewModel = mainViewModel)
        }
        composable(BottomNavigationScreens.Tasks.route) {
            IssueList(
                viewModel = issuesViewModel,
                navHostController = mainNavController
            )
        }
    }
}

@Composable
private fun BottomNavigation(
    nestedNavController: NavHostController,
    items: List<BottomNavigationScreens>,
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController = nestedNavController)
        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        nestedNavController.navigate(screen.route) {
//                            popUpTo = nestedNavController.graph.startDestination
                            launchSingleTop = true
                            popUpTo(nestedNavController.graph.startDestinationId)
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
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    issuesViewModel: IssuesViewModel,
    mainNavController: NavHostController,
) {
    val nestedNavController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Projects,
        BottomNavigationScreens.Tasks,
        BottomNavigationScreens.Profile
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
                nestedNavController = nestedNavController,
                items = bottomNavigationItems,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mainNavController.navigate(Destinations.AddProject) },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new")
            }
        },

    ) {
        BottomNavBarConfiguration(
            nestedNavController = nestedNavController,
            mainViewModel = mainViewModel,
            issuesViewModel = issuesViewModel,
            mainNavController = mainNavController,
        )
    }
}
