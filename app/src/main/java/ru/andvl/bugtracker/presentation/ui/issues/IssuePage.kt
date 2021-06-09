package ru.andvl.bugtracker.presentation.ui.issues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andvl.bugtracker.model.Comment
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.presentation.ui.custom.BigCenteredText
import ru.andvl.bugtracker.presentation.ui.custom.Title
import ru.andvl.bugtracker.utils.convertToTime

@Composable
fun IssuePage(
    viewModel: IssuesViewModel,
    issueId: Int
) {
    // description
    // ---
    // comments
    // text field | send
    viewModel.getIssue(issueId)
    viewModel.updateComments(issueId)
    val issue = viewModel.selectedIssue.collectAsState()
    val assignee: MutableState<User?> =
        remember { mutableStateOf(null) }
    val comments = viewModel.commentsList.collectAsState()
    issue.value.assigneeId?.let {
        viewModel.getUser(it)
        assignee.value = viewModel.assignee.value
    }
    if (issueId == -1) {
        BigCenteredText(text = "Error!")
    } else {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column {
                IssueDetails(issue = issue.value, assignee = assignee.value)
                Divider(
                    color = Color.Black,
                    thickness = 1.dp
                )
                CommentsBlock(comments.value)
            }
            CommentInput(
                onSendClicked = { commentText ->
                    viewModel.addComment(
                        issueId = issueId,
                        text = commentText,
                    )
                    viewModel.updateComments(issueId)
                }
            )
        }
    }
}

@Composable
private fun IssueDetails(
    issue: Issue,
    assignee: User?
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Title(value = issue.issueName)
        Text(text = issue.description)
        assignee?.let { user ->
            Text("Assigned to ${user.name}")
        }
        issue.deadline?.let {
            val deadline = it.convertToTime()
            Text(text = "Deadline: $deadline")
        }

    }
}

@Composable
private fun CommentsBlock(
    comments: List<Comment>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(comments) { comment ->
            CommentItem(
                author = comment.author.name,
                text = comment.text,
            )
            Divider(
                color = Color.Black,
                thickness = .5.dp
            )
        }
    }
}

@Composable
private fun CommentItem(
    author: String,
    text: String,
){
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                text = author,
                fontWeight = FontWeight.Bold
            )
            Text(text = text)
        }
    }
}

@Composable
private fun CommentInput(
    onSendClicked: (text: String) -> Unit = {}
) {
    val commentText = remember {
        mutableStateOf("")
    }
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = commentText.value,
            onValueChange = { commentText.value = it },
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = { onSendClicked(commentText.value) },
            modifier = Modifier.weight(.15f)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send comment"
            )
        }
    }
}

@Preview(
    backgroundColor = android.graphics.Color.WHITE.toLong()
)
@Composable
private fun IssuePagePreview() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        val testIssue = Issue(
            id = 1,
            description = "Test issue 1 preview",
            issueName = "Test 1",
            projectId = -1,
            authorId = 1,
        )
        val testUser = User(
            login = "a@b.c",
            name = "User322",
            userId = 1
        )
        Column {
            IssueDetails(
                issue = testIssue,
                assignee = testUser,
            )
            Divider(
                color = Color.Black,
                thickness = 1.dp
            )
            CommentsBlock(listOf(
                Comment(
                    id = 1,
                    author = testUser,
                    publicationDate = System.currentTimeMillis(),
                    text = "test text for a comment"
                )
            ))
        }
        CommentInput()
    }
}