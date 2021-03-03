package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.User

interface ApiHelper {

    suspend fun getUser(user: LoginUser): ApiResponse<User>

    suspend fun login(user: LoginUser): ApiResponse<User>

    suspend fun register(user: LoginUser): ApiResponse<User>

    suspend fun checkEmail(user: LoginUser): ApiResponse<Boolean>
}
