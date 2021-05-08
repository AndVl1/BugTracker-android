package ru.andvl.bugtracker.presentation.ui.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    value: String,
) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        fontSize = 36.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun SubTitle(
    value: String
) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
    )
}
