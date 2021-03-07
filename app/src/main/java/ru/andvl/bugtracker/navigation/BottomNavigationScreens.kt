package ru.andvl.bugtracker.navigation

import androidx.annotation.StringRes
import ru.andvl.bugtracker.R

sealed class BottomNavigationScreens (
    val route: String,
    @StringRes val resourceId: Int,
//    val icon: VectorAsset,
) {
    object Projects: BottomNavigationScreens("projects_page", R.string.projects_page)
    object Tasks: BottomNavigationScreens("tasks_page", R.string.tasks_page)
    object Profile: BottomNavigationScreens("profile_page", R.string.profile_page)
}