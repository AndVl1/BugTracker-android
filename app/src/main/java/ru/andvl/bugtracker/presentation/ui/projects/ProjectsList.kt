package ru.andvl.bugtracker.presentation.ui.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.model.Project
import timber.log.Timber

@Composable
fun ProjectList(
    viewModel: MainViewModel,
    navController: NavHostController? = null,
) {
    viewModel.loadProjects()
    viewModel.getProjects()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Bug Tracker") })
        },
    ) {
        LazyProjectsColumn(
            projects = viewModel.projectsList,
            onReload = { viewModel.loadProjects() },
            navController = navController,
        )
    }
}

@Composable
private fun LazyProjectsColumn(
    projects: StateFlow<List<Project>>,
    onReload: () -> Unit = {},
    navController: NavHostController? = null,
) {
    Timber.d("there are ${projects.value.size} projects")

    val projectsState = projects.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 56.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item {
            Button(
                onClick = { onReload() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Reload")
            }
        }

        items(projectsState.value) { project ->
            ProjectCard(
                id = project.id,
                name = project.name,
                description = project.description,
                issuesCount = project.issuesCount,
                navController = navController,
            )
        }
    }
}

@Preview
@Composable
fun ProjectsPreview() {

    LazyProjectsColumn(
        projects = MutableStateFlow(
            listOf(
                Project(0, "Project 1", "D1", 1),
                Project(1, "Project 2", "D2",2),
                Project(2, "Project 3", "D3", 0),
                Project(3, "Project 4", "D4", 3),
                Project(4, "Project 5", "D5", 8),
            )
        ).asStateFlow()
    )
}
