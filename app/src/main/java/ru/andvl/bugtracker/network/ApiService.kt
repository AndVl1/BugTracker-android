package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User

interface ApiService {

    @POST("/login")
    suspend fun login(
        @Query("email")
        login: String,
        @Query("password")
        password: String
    ): ApiResponse<User>

    @POST("/register")
    suspend fun register(
        @Query("email")
        email: String,
        @Query("name")
        nickname: String,
        @Query("password")
        password: String
    ): ApiResponse<User>

    @GET("/user")
    suspend fun getUserData(): ApiResponse<User>

    @POST("/check")
    suspend fun checkEmail(
        @Query("email")
        email: String
    ): ApiResponse<String>

    @GET("/projects")
    suspend fun getProjects(
        @Query("userId")
        userId: Int,
    ): ApiResponse<List<Project>>
}
