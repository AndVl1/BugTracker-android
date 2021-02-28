package ru.andvl.bugtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
	val login = mutableStateOf("")
	val password = mutableStateOf("")
	val passwordVisibility = mutableStateOf(false)

	fun onLoginChanged(login: String) {
		this.login.value = login
	}

	fun onPasswordChanged(password: String) {
		this.password.value = password
	}

	fun onPasswordVisibilityChanged() {
		passwordVisibility.value = !passwordVisibility.value
	}
}