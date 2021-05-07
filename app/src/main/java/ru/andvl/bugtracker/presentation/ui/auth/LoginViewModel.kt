package ru.andvl.bugtracker.presentation.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.presentation.datastore.LoginStatus
import ru.andvl.bugtracker.repository.MainRepository
import timber.log.Timber
import javax.inject.Inject

// can be deleted
class LoginViewModel (
    private val mainRepository: MainRepository
) : ViewModel() {
    val login = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)

    private val _isAuthenticationSuccessful: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthenticationSuccessful = _isAuthenticationSuccessful.asStateFlow()

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
                    _isAuthenticationSuccessful.emit(this.statusCode == StatusCode.OK)
                    mainRepository.setLoginStatus(
                        status = if (this.statusCode == StatusCode.OK)
                            LoginStatus.LOGGED_IN
                        else
                            LoginStatus.NOT_LOGGED_IN
                    )
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
}
