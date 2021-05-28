package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Query
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User

interface ApiHelper {

    suspend fun getUser(userId: Int): ApiResponse<User>

    suspend fun login(user: LoginUser): ApiResponse<User>

    suspend fun register(
        email: String,
        nickname: String,
        password: String
    ): ApiResponse<User>

    suspend fun checkEmail(email: String): ApiResponse<String>

    suspend fun getProjects(userId: Int): ApiResponse<List<Project>>

    suspend fun addProject(
        userId: Int,
        projectName: String,
        projectDescription: String
    ): ApiResponse<Project>

    suspend fun getIssuesForProject(
        projectId: Int
    ): ApiResponse<List<Issue>>

    suspend fun getIssuesForAssignee(
        userId: Int
    ): ApiResponse<List<Issue>>

    suspend fun addIssue(
        projectId: Int,
        issue: Issue,
    ): ApiResponse<Issue>
}
