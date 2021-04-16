package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    // override suspend fun getUser(): ApiResponse<User> = apiService.getUserData()
    override suspend fun getUser(user: LoginUser): ApiResponse<User> =
        apiService.getUserData()

    override suspend fun login(user: LoginUser): ApiResponse<User> =
        apiService.login(
            login = user.login,
            password = user.password,
        )

    override suspend fun register(
        email: String,
        nickname: String,
        password: String,
    ): ApiResponse<User> = apiService.register(
        email = email,
        nickname = nickname,
        password = password,
    )

    override suspend fun checkEmail(email: String): ApiResponse<String> =
        apiService.checkEmail(email = email)

    override suspend fun getProjects(userId: Int): ApiResponse<List<Project>> =
        apiService.getProjects(userId = userId)
}
