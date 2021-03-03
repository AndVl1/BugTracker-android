package ru.andvl.bugtracker.repository

import javax.inject.Inject
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.network.ApiHelper

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) : Repository {

    suspend fun login(user: LoginUser) = apiHelper.login(user)

    suspend fun register(user: LoginUser) = apiHelper.register(user)

    suspend fun checkEmail(user: LoginUser) = apiHelper.checkEmail(user)
}
