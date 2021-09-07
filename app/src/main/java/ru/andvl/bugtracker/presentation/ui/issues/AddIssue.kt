package ru.andvl.bugtracker.presentation.ui.issues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.material.datepicker.MaterialDatePicker
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.model.Label
import ru.andvl.bugtracker.model.User
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

@Composable
fun AddIssue(
    projectId: Int,
    viewModel: IssuesViewModel? = null,
    mainViewModel: MainViewModel,
    navHostController: NavHostController? = null,
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dateDialog = remember { MaterialDialog() }
    val assigneeDialog = remember { MaterialDialog() }
    val labelDialog = remember { MaterialDialog() }
    val selectedDate: MutableState<LocalDateTime?> =
        remember { mutableStateOf(null) }
    val assignee: MutableState<Int?> =
        remember { mutableStateOf(null) }
    val labelId: MutableState<Int> =
        remember { mutableStateOf(1) }

    mainViewModel.getAssigneeList(projectId)
    val assigneeVariants = mainViewModel.users.collectAsState()

    dateDialog.build {
        datepicker { date ->
            selectedDate.value = date.atStartOfDay()
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    assigneeDialog.build {
        listItemsSingleChoice(
            list = assigneeVariants.value.toList()
                .map { user -> user.name }
        ) { selected ->
            Timber.d(assigneeVariants.value.toList()[selected].toString())
            assignee.value = assigneeVariants.value.toList()[selected].userId
        }

        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    labelDialog.build {
        listItemsSingleChoice(
            list = Label.values()
                .toList()
                .filter { label -> label != Label.ERROR }
                .map { label -> label.name },
            initialSelection = labelId.value - 1,
        ) { selected ->
            labelId.value = selected + 1
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mainViewModel.addIssue(
                        name = title.value,
                        description = description.value,
                        date = selectedDate.value
                            ?.toInstant(ZoneOffset.UTC)?.toEpochMilli() ?: -1,
                        assigneeId = assignee.value,
                        projectId = projectId,
                        labelId = labelId.value
                    )
                    mainViewModel.getIssues(projectId)
                    navHostController?.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Proceed to adding"
                )
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = if (projectId == -1) {
                        "Err"
                    } else {
                        "Add Issue"
                    }
                )
            })
        }
    ) {
        // name description deadline (date picker) assignee

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                singleLine = true,
                label = { Text("Title") },
            )
            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                label = { Text(text = "Description") }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { dateDialog.show() },
                ) {
                    Text(text = "Deadline")
                }
                Button(
                    onClick = { assigneeDialog.show() },
                ) {
                    Text(text = "Choose assignee")
                }
                Button(onClick = { labelDialog.show() }) {
                    Text(text = "Label")
                }
            }
        }
    }
}

@Composable
@Preview
private fun AddIssuePreview() {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dateDialog = remember { MaterialDialog() }
    val assigneeDialog = remember { MaterialDialog() }
    val labelDialog = remember { MaterialDialog() }
    val selectedDate: MutableState<LocalDateTime?> =
        remember { mutableStateOf(null) }
    val assignee: MutableState<Int?> =
        remember { mutableStateOf(null) }
    val labelId: MutableState<Int> =
        remember { mutableStateOf(1) }

    dateDialog.build {
        datepicker { date ->
            selectedDate.value = date.atStartOfDay()
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    assigneeDialog.build {
        listItemsSingleChoice(
            list = listOf()
        ) { selected ->
            assignee.value = selected
        }

        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    labelDialog.build {
        listItemsSingleChoice(
            list = Label.values()
                .toList()
                .filter { label -> label != Label.ERROR }
                .map { label -> label.name },
            initialSelection = labelId.value - 1,
        ) { selected ->
            labelId.value = selected + 1
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Proceed to adding"
                )
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Err"
                )
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                singleLine = true,
                label = { Text("Title") },
            )
            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                label = { Text(text = "Description") }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { dateDialog.show() },
                ) {
                    Text(text = "Deadline")
                }
                Button(
                    onClick = { assigneeDialog.show() },
                ) {
                    Text(text = "Choose assignee")
                }
                Button(onClick = { labelDialog.show() }) {
                    Text(text = "Label")
                }
            }
            Text(
                text =
                "${selectedDate.value?.toInstant(ZoneOffset.UTC)?.toEpochMilli() }\n" +
                        "${System.currentTimeMillis()}"
            )
        }
    }
}
