package ru.andvl.bugtracker.repository

import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.persistence.UserDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
) : Repository {

    suspend fun login(user: LoginUser) = apiHelper.login(user)

    suspend fun register(user: LoginUser) = apiHelper.register(
        email = user.login,
        nickname = user.nickname,
        password = user.password,
    )

    suspend fun checkEmail(email: String) = apiHelper.checkEmail(email)

    suspend fun insertUser(user: User) = userDao.insertUser(user)
}
