package ru.andvl.bugtracker.repository

import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import ru.andvl.bugtracker.model.Comment
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

    // AUTH ----

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

    fun getCurrentUser() = userDao.getUser(getUserId())

    suspend fun getUser(id: Int) = apiHelper.getUser(id)

    // PROJECTS ----

    /** Loading projects for user
     * @param id - user id */
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

    /** Loading all projects for current user
     * User id loading from data store */
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

    /** Load issue for user
     * [id] - user id */
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

    /** Load projects for current user
     * User id loading from data store*/
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

    fun getProjects(): Flow<List<Project>> = projectDao.getProjects()

    fun getProject(id: Int): Flow<Project> = projectDao.getProject(id)

    suspend fun addProject(project: Project) =
        apiHelper.addProject(getUserId(), project.name, project.description)

    // ISSUES ----

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

    suspend fun addIssue(
        name: String,
        description: String,
        date: Long,
        assigneeId: Int?,
        projectId: Int
    ): Issue? {
        val issue = Issue(
            id = 0,
            description = description,
            issueName = name,
            projectId = projectId,
            deadline = date,
            assigneeId = assigneeId,
            authorId = getUserId()
        )
        var returned: Issue? = null
        apiHelper.addIssue(issue).suspendOnSuccess {
            returned = data
        }
        return returned
    }

    fun getIssue(id: Int) = issueDao.getIssue(id)
    // COMMENTS ---

    suspend fun getComments(issueId: Int): List<Comment> {
        val comments = ArrayList<Comment>()
        apiHelper.getComments(issueId)
            .suspendOnSuccess {
                data?.forEach { comment ->
                    comments.add(comment)
                }
            }
        return comments
    }

    suspend fun addComment(
        issueId: Int,
        authorId: Int = getUserId(),
        date: Long = System.currentTimeMillis(),
        text: String
    ) {
        apiHelper.addComment(issueId, authorId, date, text)
    }
}
