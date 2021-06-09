package ru.andvl.bugtracker.presentation.ui.issues

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.navigation.Destinations
import ru.andvl.bugtracker.presentation.ui.custom.BigCenteredText
import ru.andvl.bugtracker.presentation.ui.custom.SubTitle
import ru.andvl.bugtracker.presentation.ui.custom.Title

// сверху - picker для фильтров по статусу и лейблу.
// по умолчанию - показываются все
/** Issue, according to selected project */
@Composable
fun ProjectsIssuesPage(
    projectId: Int,
    navHostController: NavHostController? = null,
    viewModel: MainViewModel,
){
    viewModel.getIssues(projectId)
    viewModel.getProject(projectId)
    val project = viewModel.selectedProject.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = project.value.name) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController?.popBackStack()
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
            FloatingActionButton(
                onClick = {
                    navHostController
                        ?.navigate("${Destinations.AddIssue}/$projectId") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new issue")
            }
        }
    ) {
        LazyIssueColumn(issues = viewModel.issuesList)
    }
}

/** Issues, assigned to user
 * Part of bottom navigation */
@ExperimentalAnimationApi
@Composable
fun IssueList(
    viewModel: IssuesViewModel
) {
    viewModel.getIssues()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Bug Tracker") })
        },
    ) {
        val filterExpanded = viewModel.isFilterExpanded.collectAsState()
        Column {
            ExpandableCard(
                onCardArrowClick = { viewModel.onArrowClicked() }, 
                expanded = filterExpanded.value
            )
            LazyIssueColumn(issues = viewModel.issuesList)
        }
    }
}

@Composable
private fun LazyIssueColumn(
    issues: StateFlow<List<Issue>>
) {
    val issuesState = issues.collectAsState()

    if (issuesState.value.isNotEmpty()) {
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
            items(issuesState.value) { issue ->
                IssueCard(
                    id = issue.projectIssueNumber,
                    name = issue.issueName,
                    description = issue.description
                )
            }
        }
    } else {
        BigCenteredText(text = "No Issues")
    }
}

@Preview
@Composable
private fun IssuesListPreview() {
    LazyIssueColumn(
        issues = MutableStateFlow(
            listOf(
                Issue(
                    id = 0,
                    issueName = "Test",
                    description = "Descr",
                    projectIssueNumber = 1,
                    projectId = -1,
                    authorId = 1,
                ),
                Issue(
                    id = 4,
                    issueName = "Test2",
                    description = "Descr 13345",
                    projectIssueNumber = 3,
                    projectId = -1,
                    authorId = 1,
                ),
                Issue(
                    id = 6,
                    issueName = "Test",
                    description = "Description 12345",
                    projectIssueNumber = 5,
                    projectId = -1,
                    authorId = 1,
                ),
            )
        ).asStateFlow()
    )
}
