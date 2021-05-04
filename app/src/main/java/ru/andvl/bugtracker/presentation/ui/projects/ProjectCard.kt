package ru.andvl.bugtracker.presentation.ui.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProjectCard(
    name: String,
    description: String,
    issuesCount: Int
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier.clickable(onClick = {})
    ) {
        val layoutPadding = 8.dp

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(layoutPadding)
        ) {
            val (
                nameRefs,
                descriptionRefs,
                countRefs,
            ) = createRefs()

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(nameRefs) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            )

            Text(
                text = description,
                modifier = Modifier.constrainAs(descriptionRefs) {
                    top.linkTo(nameRefs.bottom)
                    start.linkTo(nameRefs.start)
                },
            )

            Text(
                text = "$issuesCount issues",
                modifier = Modifier.constrainAs(countRefs) {
                    top.linkTo(descriptionRefs.bottom)
                    end.linkTo(parent.end)
                },
            )
        }
    }
}

@Preview
@Composable
fun ProjectCardPreview() {
    ProjectCard(
        name = "Name 1",
        description = "Description Blablabla",
        issuesCount = 5
    )
}