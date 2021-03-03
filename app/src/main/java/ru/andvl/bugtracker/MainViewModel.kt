package ru.andvl.bugtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.repository.MainRepository

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    // Login, register
    // TODO: separate in different VMs
    val login = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)

    private val _isEmailAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailAvailable = _isEmailAvailable.asStateFlow()

    fun onLoginChanged(login: String) {
        this.login.value = login
    }

    fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    fun onPasswordVisibilityChanged() {
        passwordVisibility.value = !passwordVisibility.value
    }

    fun onCheckEmail(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainRepository.checkEmail(LoginUser(login = email, password = ""))
            }
        }
    }

    fun onLoginButtonClickListener() {
        /* TODO */
    }

    fun onRegisterClickListener() {
        /* TODO */
    }
    // ---------------------
}
