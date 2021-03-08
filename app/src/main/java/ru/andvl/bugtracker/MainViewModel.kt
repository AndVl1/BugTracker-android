package ru.andvl.bugtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val loginButtonActor = viewModelScope.actor<LoginUser> {
        withContext(Dispatchers.IO) {
            for (user in channel) {
                mainRepository.login(
                    user
                ).suspendOnSuccess {
                    Timber.d(statusCode.code.toString())
                    _areLoginAndPasswordCorrect.emit(this.statusCode == StatusCode.OK)
                }.suspendOnError {
                    Timber.d(statusCode.code.toString())
                    _areLoginAndPasswordCorrect.emit(false)
                }.onException {
                    Timber.d(this.message)
                }
            }
        }
    }

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
        loginButtonActor.offer(
            LoginUser(
                login = login.value, password = password.value
            )
        )
    }

    fun onRegisterClickListener() {
        /* TODO */
    }

    /** Check email string */
    private val emailRegex = Regex("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}")

    val emailCheckString = mutableStateOf("")
    private val _isEmailAvailable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isEmailAvailable = _isEmailAvailable.asStateFlow()

    private val _canNavigateToNext: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val canNavigateToNext = _canNavigateToNext.asStateFlow()

    private val emailChangesActorChannel = viewModelScope.actor<String> {
        withContext(Dispatchers.IO) {
            for (change in channel) {
                if (!emailRegex.matches(emailCheckString.value)){
                    Timber.d("Wrong email")
                    _isEmailAvailable.emit(false)
                } else {
                    Timber.d("Checking")
                    mainRepository.checkEmail(
                        change
                    ).suspendOnSuccess {
                        Timber.d("Success")
                        _isEmailAvailable.emit(true)
                        _canNavigateToNext.emit(true)
                    }.suspendOnError {
                        Timber.d("Error")
                        _isEmailAvailable.emit(false)
                    }.onException {
                        Timber.e(this.exception)
                    }
                }
            }
        }
    }

    fun onEmailInputChanged() {
        Timber.d(emailCheckString.value)
        _isEmailAvailable.value = emailRegex.matches(emailCheckString.value)
        if (emailRegex.matches(emailCheckString.value)) {
            emailChangesActorChannel.offer(emailCheckString.value)
        }
    }

    fun checkEmail() {
        emailChangesActorChannel.offer(emailCheckString.value)
    }

    /** Nickname input */
    val nickname = mutableStateOf("")
    private val _isRegistrationSuccessful: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRegistrationSuccessful = _isRegistrationSuccessful.asStateFlow()

    /** Password input for registration */
    val newUserPassword = mutableStateOf("")

    private val registerActorChannel = viewModelScope.actor<LoginUser> {
        withContext(Dispatchers.IO) {
            for (user in channel) {
                mainRepository.register(
                    user
                ).suspendOnSuccess {
                    Timber.d(this.data.toString())
                    _isRegistrationSuccessful.emit(this.statusCode == StatusCode.OK)
                }.suspendOnError {
                    Timber.d(statusCode.code.toString())
                    _isRegistrationSuccessful.emit(false)
                }.onException {
                    Timber.d(this.message)
                }
            }
        }
    }

    fun onRegisterClicked() {
        registerActorChannel.offer(
            LoginUser(
                login = emailCheckString.value,
                password = newUserPassword.value,
                nickname = nickname.value
        ))
    }
}
