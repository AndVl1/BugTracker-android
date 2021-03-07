package ru.andvl.bugtracker.ui.custom

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CircleProgressIndicator(
    isVisible: Boolean,
    modifier: Modifier
){
    if (isVisible) {
        CircularProgressIndicator(modifier = modifier)
    }
}