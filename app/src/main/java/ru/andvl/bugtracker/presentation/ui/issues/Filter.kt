package ru.andvl.bugtracker.presentation.ui.issues

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andvl.bugtracker.presentation.ui.custom.CardArrow
import ru.andvl.bugtracker.presentation.ui.custom.CardTitle
import ru.andvl.bugtracker.presentation.ui.custom.CustomCheckbox

@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@Composable
fun ExpandableCard(
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = FilterConstants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = FilterConstants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 24.dp else 4.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = FilterConstants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 0f else 180f
    }

    Card(
        elevation = cardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = "Filter")
            }
            ExpandableContent(visible = expanded, initialVisibility = expanded)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    initialVisibility: Boolean = false,
    visible: Boolean = false,
) {

    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FilterConstants.FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(FilterConstants.EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FilterConstants.FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(FilterConstants.COLLAPSE_ANIMATION_DURATION))
    }

    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut,
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier
                .padding(4.dp)
        ) {
            FilterContent {
                // TODO
            }
        }
    }
}

@Composable
fun FilterContent(
    onShowClickListener: () -> Unit
) {
    Column(
        Modifier.padding(4.dp)
    ) {
        FilterCheckboxes()
        Button(
            onClick = { onShowClickListener() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterPreview() {
    FilterContent {}
}

@Composable
private fun FilterCheckboxes() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val checkedState = remember { mutableStateOf(true) }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Status")
            CustomCheckbox(text = "All")
            CustomCheckbox(text = "New")
            CustomCheckbox(text = "In progress")
            CustomCheckbox(text = "Review")
            CustomCheckbox(text = "Testing")
            CustomCheckbox(text = "Ready")
            CustomCheckbox(text = "Closed")
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Label")
            CustomCheckbox(text = "All")
            CustomCheckbox(text = "DB")
            CustomCheckbox(text = "Interface")
            CustomCheckbox(text = "Docs")
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun ExpandPreview() {
    ExpandableCard(onCardArrowClick = { /*TODO*/ }, expanded = true)
}

private object FilterConstants {
    const val FADE_IN_ANIMATION_DURATION = 100
    const val EXPAND_ANIMATION_DURATION = 100
    const val FADE_OUT_ANIMATION_DURATION = 100
    const val COLLAPSE_ANIMATION_DURATION = 100
}
