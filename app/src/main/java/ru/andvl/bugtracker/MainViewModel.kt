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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User
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

    private val _areLoginAndPasswordCorrect: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areLoginAndPasswordCorrect = _areLoginAndPasswordCorrect.asStateFlow()

    private val loginButtonActor = viewModelScope.actor<LoginUser> {
        withContext(Dispatchers.IO) {
            for (user in channel) {
                mainRepository.login(
                    user
                ).suspendOnSuccess {
                    Timber.d("user ${statusCode.code} $data")
                    this.data?.let {
                        mainRepository.setUserId(it.userId)
                        mainRepository.insertUser(it)
                        loadProjects(it.userId)
                        loadIssuesForUser(it.userId)
                    }
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

    fun onLoginButtonClickListener() {
        loginButtonActor.trySend(
            LoginUser(
                login = login.value, password = password.value
            )
        ).isSuccess
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
                if (!emailRegex.matches(emailCheckString.value)) {
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
                        getProjectsInitial()
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
                    this.data?.let {
                        mainRepository.insertUser(it)
                        mainRepository.setUserId(it.userId)
                    }
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
    private val _selectedProject: MutableStateFlow<Project> = MutableStateFlow(
        Project(
            id = -1,
            name = "Loading...",
            description = ""
        )
    )
    val selectedProject = _selectedProject.asStateFlow()

    private fun loadProjects(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("Projects?")
                mainRepository.loadProjects(id)
            }
        }
    }

    fun loadProjects() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("Projects?")
                mainRepository.loadProjects()
            }
        }
    }

    private suspend fun getProjectsInitial() {
        mainRepository.getProjects()
            .collect {
                _projectsList.emit(it)
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

    fun getProject(id: Int) {
        viewModelScope.launch {
            mainRepository.getProject(id)
                .collect {
                    _selectedProject.emit(it)
                }
        }
    }

    private val _issuesList: MutableStateFlow<List<Issue>> =
        MutableStateFlow(ArrayList())
    val issuesList = _issuesList.asStateFlow()

    fun getIssues(projectId: Int) {
        viewModelScope.launch {
            _issuesList.emit(
                mainRepository
                    .loadIssues(projectId)
            )
        }
    }

    /** Add Project */
    val projectName = mutableStateOf("")
    val projectDescription = mutableStateOf("")

    fun addProject(
        project: Project = Project(
            id = 0,
            name = projectName.value,
            description = projectDescription.value,
        )
    ) {
        viewModelScope.launch {
            mainRepository.addProject(project)
        }
        projectName.value = ""
        projectDescription.value = ""
        loadProjects()
    }

    /** Profile */
    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()
    private val _userLogin = MutableStateFlow("")
    val userLogin = _userLogin.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    mainRepository.getCurrentUser().collect {
                        _userName.value = it.name
                        _userLogin.value = it.login
                    }
                } catch (e: Exception) {
                    _userName.value = "Reload"
                    _userLogin.value = "the app"
                }
            }
        }
    }

    /** Issues */
    private fun loadIssuesForUser(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainRepository.loadIssuesForUser(id)
            }
        }
    }

    fun addIssue(
        name: String,
        description: String,
        date: Long,
        assigneeId: Int?,
        projectId: Int,
        labelId: Int,
    ) {
        viewModelScope.launch {
            mainRepository.addIssue(
                name = name,
                description = description,
                date = date,
                assigneeId = assigneeId,
                projectId = projectId,
                labelId = labelId
            )
        }
    }

    private val _users = MutableStateFlow(arrayListOf<User>())
    val users = _users.asStateFlow()

    fun getAssigneeList(projectId: Int) {
        viewModelScope.launch {
            _users.emit(
                mainRepository.getUsersForProject(projectId)
            )
        }
    }
}
