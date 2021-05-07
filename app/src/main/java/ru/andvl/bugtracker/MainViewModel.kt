package ru.andvl.bugtracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.presentation.datastore.LoginStatus
import ru.andvl.bugtracker.repository.MainRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val isLoggedIn = mainRepository.checkLogin()

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
                    Timber.d("user ${statusCode.code} ${response.body()?.userId}")
                    _areLoginAndPasswordCorrect.emit(this.statusCode == StatusCode.OK)
                    _isAuthenticationSuccessful.emit(this.statusCode == StatusCode.OK)
                    mainRepository.setLoginStatus(
                        status = if (this.statusCode == StatusCode.OK)
                            LoginStatus.LOGGED_IN
                        else
                            LoginStatus.NOT_LOGGED_IN
                    )
                    mainRepository.setUserId(this.response.body()?.userId ?: -1)
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
            emailChangesActorChannel.trySend(emailCheckString.value).isSuccess
        }
    }

    fun checkEmail() {
        emailChangesActorChannel.trySend(emailCheckString.value).isSuccess
    }

    /** Nickname input */
    val nickname = mutableStateOf("")
    private val _isAuthenticationSuccessful: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthenticationSuccessful = _isAuthenticationSuccessful.asStateFlow()

    /** Password input for registration */
    val newUserPassword = mutableStateOf("")

    private val registerActorChannel = viewModelScope.actor<LoginUser> {
        withContext(Dispatchers.IO) {
            for (user in channel) {
                mainRepository.register(
                    user
                ).suspendOnSuccess {
                    Timber.d(this.data.toString())
                    _isAuthenticationSuccessful.emit(this.statusCode == StatusCode.OK)
                    mainRepository.setLoginStatus(
                        status = if (this.statusCode == StatusCode.OK)
                            LoginStatus.LOGGED_IN
                        else
                            LoginStatus.NOT_LOGGED_IN
                    )
                }.suspendOnError {
                    Timber.d(statusCode.code.toString())
                    _isAuthenticationSuccessful.emit(false)
                }.onException {
                    Timber.d(this.message)
                }
            }
        }
    }

    fun onRegisterClicked() {
        registerActorChannel.trySend(
            LoginUser(
                login = emailCheckString.value,
                password = newUserPassword.value,
                nickname = nickname.value
            )
        ).isSuccess
    }

    /** Projects */

    private val _projectsList: MutableStateFlow<List<Project>> = MutableStateFlow(ArrayList())
    val projectsList = _projectsList.asStateFlow()



    fun loadProjects() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("Projects?")
                mainRepository.loadProjects()
            }
        }
    }

    fun getProjects() {
        viewModelScope.launch {
            mainRepository.getProjects()
                .collect {
                    _projectsList.emit(it)
                }
        }
    }

    /** Add Project */
    val projectName = mutableStateOf("")
    val projectDescription = mutableStateOf("")

    fun addProject(
        project: Project = Project(
            id = 0,
            name = projectName.value,
            description = projectDescription.value
        )
    ) {
        viewModelScope.launch {
            mainRepository.addProject(project)
        }
        projectName.value = ""
        projectDescription.value = ""
        loadProjects()
    }
}
