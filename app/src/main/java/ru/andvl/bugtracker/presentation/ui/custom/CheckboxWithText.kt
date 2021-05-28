package ru.andvl.bugtracker.presentation.ui.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckbox(
    text: String
) {
    val checkedState = remember { mutableStateOf(true) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {checkedState.value = it}
        )
        Text(
            text = text,
        )
    }
}

@Composable
@Preview
private fun CheckboxPreview() {
    CustomCheckbox(text = "Test 1 2 3")
}
