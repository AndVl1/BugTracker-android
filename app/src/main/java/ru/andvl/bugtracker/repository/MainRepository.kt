package ru.andvl.bugtracker.repository

import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.persistence.IssueDao
import ru.andvl.bugtracker.persistence.ProjectDao
import ru.andvl.bugtracker.persistence.UserDao
import ru.andvl.bugtracker.presentation.datastore.DataStoreManager
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
    private val projectDao: ProjectDao,
    private val issueDao: IssueDao,
    private val dataStoreManager: DataStoreManager,
) : Repository {

    suspend fun login(user: LoginUser) = apiHelper.login(user)

    suspend fun register(user: LoginUser) = apiHelper.register(
        email = user.login,
        nickname = user.nickname,
        password = user.password,
    )

    suspend fun checkEmail(email: String) = apiHelper.checkEmail(email)

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun checkLogin() = dataStoreManager.isLoggedIn

    suspend fun setLoginStatus(status: Int) = dataStoreManager.setLoginStatus(status)

    suspend fun setUserId(id: Int) = dataStoreManager.setCurrentUserId(id)

    private fun getUserId() =
        dataStoreManager.currentUserId

    suspend fun loadProjects(id: Int) {
        val projects = arrayListOf<Project>()
        apiHelper.getProjects(id)
            .suspendOnSuccess {
                this.data?.forEach {
                    projects.add(it)
                }
            }.suspendOnException {
                Timber.e(exception.localizedMessage)
            }
        projects.forEach {
            Timber.d(it.toString())
            projectDao.insertProject(it)
        }
    }

    suspend fun loadProjects() {
        val projects = arrayListOf<Project>()
        apiHelper.getProjects(getUserId())
            .suspendOnSuccess {
                this.data?.forEach {
                    projects.add(it)
                }
            }.suspendOnException {
                Timber.e(exception.localizedMessage)
            }
        projects.forEach {
            Timber.d(it.toString())
            projectDao.insertProject(it)
        }
    }

    suspend fun loadIssuesForUser(id: Int) {
        val issues = arrayListOf<Issue>()
        apiHelper.getIssuesForAssignee(id)
            .suspendOnSuccess {
                this.data?.forEach {
                    issues.add(it)
                }
            }.suspendOnException {
                Timber.e(exception.localizedMessage)
            }
        issues.forEach {
            issueDao.insertIssue(it)
        }
    }

    suspend fun loadIssuesForUser() {
        val issues = arrayListOf<Issue>()
        apiHelper.getIssuesForAssignee(getUserId())
            .suspendOnSuccess {
                this.data?.forEach {
                    issues.add(it)
                }
            }.suspendOnException {
                Timber.e(exception.localizedMessage)
            }
        issues.forEach {
            issueDao.insertIssue(it)
        }
    }

    fun getIssues(): Flow<List<Issue>> = issueDao.getIssues()

    suspend fun loadIssues(projectId: Int): List<Issue> {
        val issuesResponse = apiHelper.getIssuesForProject(projectId)
        val issues = arrayListOf<Issue>()
        issuesResponse
            .suspendOnSuccess {
                this.data?.forEach {
                    issues.add(it)
                }
            }
            .suspendOnException {
                Timber.e(this.exception)
            }
        return issues
    }


    fun getProjects(): Flow<List<Project>> = projectDao.getProjects()

    fun getProject(id: Int): Flow<Project> = projectDao.getProject(id)

    suspend fun addProject(project: Project) =
        apiHelper.addProject(getUserId(), project.name, project.description)

    fun getUser() = userDao.getUser(getUserId())
}
