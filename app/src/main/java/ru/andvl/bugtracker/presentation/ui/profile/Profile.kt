package ru.andvl.bugtracker.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.presentation.ui.custom.SubTitle
import ru.andvl.bugtracker.presentation.ui.custom.Title

@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
) {
    viewModel.getUser()
    val name = viewModel.userName.collectAsState("")
    val login = viewModel.userLogin.collectAsState("")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Bug Tracker") } )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Title(value = name.value)
                SubTitle(value = login.value)
            }
        }
    }
}