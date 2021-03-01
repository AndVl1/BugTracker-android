package ru.andvl.bugtracker.repository

import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.network.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
	private val apiHelper: ApiHelper
) : Repository {

	suspend fun login(user: LoginUser) = apiHelper.login(user)

	suspend fun register(user: LoginUser) = apiHelper.register(user)

}