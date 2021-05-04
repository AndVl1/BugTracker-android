package ru.andvl.bugtracker.repository

import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.collect
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.persistence.UserDao
import ru.andvl.bugtracker.presentation.datastore.DataStoreManager
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
    private val dataStoreManager: DataStoreManager
) : Repository {

    suspend fun login(user: LoginUser) = apiHelper.login(user)

    suspend fun register(user: LoginUser) = apiHelper.register(
        email = user.login,
        nickname = user.nickname,
        password = user.password,
    )

    suspend fun checkEmail(email: String) = apiHelper.checkEmail(email)

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun checkLogin() = dataStoreManager.isLoggedIn2

    suspend fun setLoginStatus(status: Int) = dataStoreManager.setLoginStatus(status)
}
