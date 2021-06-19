package ru.andvl.bugtracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.presentation.ui.auth.CheckEmailPage
import ru.andvl.bugtracker.presentation.ui.auth.LoginPage
import ru.andvl.bugtracker.presentation.ui.auth.PasswordNicknamePage
import ru.andvl.bugtracker.presentation.ui.issues.AddIssue
import ru.andvl.bugtracker.presentation.ui.issues.IssuePage
import ru.andvl.bugtracker.presentation.ui.issues.IssuesViewModel
import ru.andvl.bugtracker.presentation.ui.issues.ProjectsIssuesPage
import ru.andvl.bugtracker.presentation.ui.projects.AddProjectScreen
import ru.andvl.bugtracker.presentation.ui.splash.SplashScreen
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun BugTrackerApp(
    mainViewModel: MainViewModel,
    issuesViewModel: IssuesViewModel,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    val loggedIn = mainViewModel.isLoggedIn.collectAsState(initial = false)
    val startDestination = Destinations.Splash
    Timber.d(startDestination)
    NavHost(navController = navController, startDestination = Destinations.Splash) {
        composable(Destinations.Splash) {
            SplashScreen(
                viewModel = mainViewModel,
                navController = navController,
            )
        }
        composable(Destinations.Login) {
            LoginPage(
                viewModel = mainViewModel,
                navController = navController,
            )
        }
        composable(Destinations.CheckEmail) {
            CheckEmailPage(
                viewModel = mainViewModel,
                navController = navController
            )
        }
        composable(Destinations.NicknamePasswordInput) {
            PasswordNicknamePage(viewModel = mainViewModel)
        }
        composable(Destinations.MainScreenNavigation) {
            MainScreen(
                mainViewModel = mainViewModel,
                mainNavController = navController,
                issuesViewModel = issuesViewModel,
            )
        }
        composable(Destinations.AddProject) {
            AddProjectScreen(
                viewModel = mainViewModel,
                navController = navController,
            )
        }
        composable(
            "${Destinations.ProjectIssues}/{projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            ProjectsIssuesPage(
                projectId = backStackEntry.arguments?.getInt("projectId") ?: -1,
                navHostController = navController,
                viewModel = mainViewModel
            )
        }
        composable(
            route = "${Destinations.AddIssue}/{projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            AddIssue(
                projectId = backStackEntry.arguments?.getInt("projectId") ?: -1,
                viewModel = issuesViewModel,
                navHostController = navController,
                mainViewModel = mainViewModel,
            )
        }
        composable(
            route = "${Destinations.IssuePage}/{issueId}",
            arguments = listOf(navArgument("issueId") { type = NavType.IntType })
        ) { backStackEntry ->
            IssuePage(
                viewModel = issuesViewModel,
                issueId = backStackEntry.arguments?.getInt("issueId") ?: -1,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}
