package ru.andvl.bugtracker.presentation.ui.projects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.presentation.ui.custom.Title

@Composable
fun AddProjectScreen (
    viewModel: MainViewModel,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add new project") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addProject()
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Continue",
                )
            }
        }
    ) {
        AddProjectContent(
            name = viewModel.projectName,
            description = viewModel.projectDescription,
        )
    }
}

@Composable
private fun AddProjectContent(
    name: MutableState<String>,
    description: MutableState<String>,
) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        Title(value = "Add new project")
        TextField(
            value = name.value,
            onValueChange = { value -> name.value = value },
            label = { Text("Project Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 1,
        )
        TextField(
            value = description.value,
            onValueChange = { value -> description.value = value },
            label = { Text("Project Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    AddProjectContent(
        name = mutableStateOf(""),
        description = mutableStateOf(""),
    )
}
