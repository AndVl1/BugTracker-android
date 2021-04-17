package ru.andvl.bugtracker.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import kotlinx.coroutines.launch
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.R
import ru.andvl.bugtracker.navigation.Destinations
import ru.andvl.bugtracker.presentation.datastore.LoginStatus

@Composable
fun SplashScreen(
    viewModel: MainViewModel,
    navController: NavController,
) {

    val isLoggedIn = viewModel.isLoggedIn
        .collectAsState(initial = LoginStatus.LOADING)
        .value

    when (isLoggedIn) {
        LoginStatus.LOGGED_IN -> {
            navController.navigate(Destinations.MainScreenNavigation) {
                popUpTo(Destinations.Splash) { inclusive = true }
            }
        }
        LoginStatus.NOT_LOGGED_IN -> {
            navController.navigate(Destinations.Login) {
                popUpTo(Destinations.Splash) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bug),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
