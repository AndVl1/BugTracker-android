package ru.andvl.bugtracker.presentation.ui.projects

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.model.Project

@Composable
fun ProjectList(
    viewModel: MainViewModel,
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(5) { index ->
            Text("$index")
        }
    }
}

@Composable
fun LazyProjectsColumn(
    projects: List<Project>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(projects) { project ->
            ProjectCard(
                name = project.name,
                description = project.description,
                issuesCount = 0
            )
        }
    }
}

@Preview
@Composable
fun ProjectsPreview() {
    LazyProjectsColumn(
        projects = listOf(
            Project(0, "Project 1", "D1"),
            Project(1, "Project 2", "D2"),
            Project(2, "Project 3", "D3"),
            Project(3, "Project 4", "D4"),
            Project(4, "Project 5", "D5"),
        )
    )
}
