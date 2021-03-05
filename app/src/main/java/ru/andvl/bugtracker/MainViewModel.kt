package ru.andvl.bugtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.repository.MainRepository
import timber.log.Timber
import javax.inject.Inject

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

    private val _areLoginAndPasswordCorrect: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areLoginAndPasswordCorrect = _areLoginAndPasswordCorrect.asStateFlow()

    fun onLoginChanged(login: String) {
        this.login.value = login
    }

    fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    fun onPasswordVisibilityChanged() {
        passwordVisibility.value = !passwordVisibility.value
    }

    fun onLoginButtonClickListener() {
        /* TODO */
    }

    fun onRegisterClickListener() {
        /* TODO */
    }

    /** Check email string */
    private val emailRegex = Regex("\\w+@\\w+.\\w+")

    val emailCheckString = mutableStateOf("")
    private val _isEmailAvailable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isEmailAvailable = _isEmailAvailable.asStateFlow()

    fun onEmailInputChanged() {
        Timber.d(emailCheckString.value)
        _isEmailAvailable.value = emailRegex.matches(emailCheckString.value)
        if (emailRegex.matches(emailCheckString.value)) {
            viewModelScope.launch {
                mainRepository.checkEmail(
                    LoginUser(
                        login = emailCheckString.value
                    )
                ).suspendOnSuccess {
                    _isEmailAvailable.value = this.data!!
                }
            }
        }
        if (_isEmailAvailable.value) {
            viewModelScope.launch {
                mainRepository.checkEmail(
                    LoginUser(
                        login = emailCheckString.value
                    )
                )
            }
        }
    }

    /** Nickname input */
    val nickname = mutableStateOf("")
    private val _isNickNameAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNicknameAvailable = _isNickNameAvailable.asStateFlow()

    fun onNicknameChanged(nick: String) {

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
    // ---------------------
}
