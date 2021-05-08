package ru.andvl.bugtracker.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ru.andvl.bugtracker.R

sealed class BottomNavigationScreens (
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int,
    val contentDescription: String,
) {
    object Projects: BottomNavigationScreens(
        route = "projects_page",
        resourceId = R.string.projects_page,
        icon = R.drawable.ic_folder_black_24dp,
        contentDescription = "Page with all projects"
    )
    object Tasks: BottomNavigationScreens(
        route = "tasks_page",
        resourceId = R.string.tasks_page,
        icon = R.drawable.ic_task_black_24dp,
        contentDescription = "Page with tasks"
    )
    object Profile: BottomNavigationScreens(
        route = "profile_page",
        resourceId = R.string.profile_page,
        icon = R.drawable.ic_person_outline_black_24dp,
        contentDescription = "Profile page"
    )
}
