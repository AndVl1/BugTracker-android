package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.User

interface ApiService {

	@POST("/login")
	suspend fun login(
		user: LoginUser,
	): ApiResponse<User>

	@POST("/register")
	suspend fun register(
		user: LoginUser,
	): ApiResponse<User>

	@GET("/user")
	suspend fun getUserData(): ApiResponse<User>
}