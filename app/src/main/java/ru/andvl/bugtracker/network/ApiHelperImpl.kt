package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.User
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
	private val apiService: ApiService
) : ApiHelper {
	//	override suspend fun getUser(): ApiResponse<User> = apiService.getUserData()
	override suspend fun getUser(user: LoginUser): ApiResponse<User> =
		apiService.getUserData()

	override suspend fun login(user: LoginUser): ApiResponse<User> =
		apiService.login(user)

	override suspend fun register(user: LoginUser): ApiResponse<User> =
		apiService.register(user)

}