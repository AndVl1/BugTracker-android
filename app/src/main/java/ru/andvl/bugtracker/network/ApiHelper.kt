package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import ru.andvl.bugtracker.model.Comment
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

    suspend fun getProjectUsers(projectId: Int): ApiResponse<List<User>>

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
        issue: Issue,
    ): ApiResponse<Issue>

    suspend fun updateIssue(
        issue: Issue,
        status: Int,
    ): ApiResponse<Issue>

    suspend fun getComments(
        issueId: Int
    ): ApiResponse<List<Comment>>

    suspend fun addComment(
        issueId: Int,
        authorId: Int,
        date: Long,
        text: String,
    ): ApiResponse<Comment>
}
