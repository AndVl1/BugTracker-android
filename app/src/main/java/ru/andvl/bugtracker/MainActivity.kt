package ru.andvl.bugtracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import ru.andvl.bugtracker.navigation.BugTrackerApp
import ru.andvl.bugtracker.presentation.ui.issues.IssuesViewModel
import ru.andvl.bugtracker.ui.theme.BugTrackerTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val issuesViewModel: IssuesViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BugTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                ) {
                    BugTrackerApp(
                        mainViewModel = mainViewModel,
                        issuesViewModel = issuesViewModel,
                    )
                }
            }
        }
    }
}
