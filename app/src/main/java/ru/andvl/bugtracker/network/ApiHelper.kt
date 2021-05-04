package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User

interface ApiHelper {

    suspend fun getUser(user: LoginUser): ApiResponse<User>

    suspend fun login(user: LoginUser): ApiResponse<User>

    suspend fun register(
        email: String,
        nickname: String,
        password: String
    ): ApiResponse<User>

    suspend fun checkEmail(email: String): ApiResponse<String>

    suspend fun getProjects(userId: Int): ApiResponse<List<Project>>
}
