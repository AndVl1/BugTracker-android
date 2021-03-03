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

    /* TODO  separate in different VMs */
    // Login, register
    /** Login page strings*/
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

    /** Check email string */
    val email = mutableStateOf("")
    private val _isEmailAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)


    val isEmailAvailable = _isEmailAvailable.asStateFlow()

    /** Nickname input */
    val nickname = mutableStateOf("")
    private val _isNickNameAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isNicknameAvailable = _isNickNameAvailable.asStateFlow()

    fun onNicknameChanged(nick: String) {
        // TODO
    }

    /** Password input for registration */
    val newUserPassword = mutableStateOf("")

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
