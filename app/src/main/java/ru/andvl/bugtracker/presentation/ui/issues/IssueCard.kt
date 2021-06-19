package ru.andvl.bugtracker.presentation.ui.issues

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.andvl.bugtracker.navigation.Destinations

@Composable
fun IssueCard(
    id: Int,
    name: String,
    description: String,
    navController: NavController? = null
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .clickable(onClick = {
                navController
                    ?.navigate("${Destinations.IssuePage}/$id")
            })
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "#$id $name",
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = description,
                maxLines = 2,
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = Color.BLUE.toLong()
)
@Composable
fun IssueCardPreview() {
    IssueCard(
        id = 1,
        name = "Test",
        description = "Test description, writing quiet big text just to test" +
                " if it will work correct for this big test aaabbb babbab b " +
                "aba  bau baubobus sfosbo"
    )
}
