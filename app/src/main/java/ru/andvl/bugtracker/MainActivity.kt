package ru.andvl.bugtracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import ru.andvl.bugtracker.presentation.ui.login.LoginPage
import ru.andvl.bugtracker.ui.theme.BugTrackerTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	val mainViewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			BugTrackerTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					color = MaterialTheme.colors.background,

					) {
					LoginPage(viewModel = mainViewModel)
				}
			}
		}
	}
}
