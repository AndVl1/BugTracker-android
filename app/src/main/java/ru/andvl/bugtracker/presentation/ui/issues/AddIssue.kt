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
import com.google.android.material.datepicker.MaterialDatePicker
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import java.time.LocalDate

@Composable
fun AddIssue(
    projectId: Int,
    viewModel: IssuesViewModel? = null,
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dateDialog = remember { MaterialDialog() }
    val assigneeDialog = remember { MaterialDialog() }
    val selectedDate: MutableState<LocalDate?> =
        remember { mutableStateOf(null) }
    val assignee: MutableState<Int?> =
        remember { mutableStateOf(null) }



    dateDialog.build {
        datepicker { date ->
            selectedDate.value = date
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }

    assigneeDialog.build {
        listItemsSingleChoice(list = listOf()) { selected ->
            assignee.value = selected
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
                    viewModel?.addIssue(
                        name = title.value,
                        description = description.value,
                        date = selectedDate.value?.toEpochDay() ?: -1,
                        assigneeId = assignee.value,
                        projectId = projectId
                    )
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
                    Text(text = "Select Date")
                }
                Button(
                    onClick = { assigneeDialog.show() },
                ) {
                    Text(text = "Choose assignee")
                }
            }
        }
    }
}

@Composable
@Preview
private fun AddIssuePreview() {
    AddIssue(projectId = -1)
}
