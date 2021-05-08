package ru.andvl.bugtracker.repository

import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.persistence.ProjectDao
import ru.andvl.bugtracker.persistence.UserDao
import ru.andvl.bugtracker.presentation.datastore.DataStoreManager
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
    private val projectDao: ProjectDao,
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

    private fun getUserId()  =
        dataStoreManager.currentUserId

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
        projects.forEach{
            Timber.d(it.toString())
            projectDao.insertProject(it)
        }
    }

    fun getProjects(): Flow<List<Project>> = projectDao.getProjects()

    suspend fun addProject(project: Project) = apiHelper.addProject(getUserId(), project.name, project.description)

    fun getUser() = userDao.getUser(getUserId())
}
