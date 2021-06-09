package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.andvl.bugtracker.model.Comment
import ru.andvl.bugtracker.model.Issue
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

    @GET("/users/{id}")
    suspend fun getUserData(
        @Path("id")
        id: Int
    ): ApiResponse<User>

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

    @POST("/projects/add")
    suspend fun addProject(
        @Query("userId")
        userId: Int,
        @Query("pname")
        projectName: String,
        @Query("pdesc")
        projectDescription: String
    ): ApiResponse<Project>

    @GET("/projects/{id}/issues")
    suspend fun getIssuesForProject(
        @Path("id")
        projectId: Int,
    ): ApiResponse<List<Issue>>

    @GET("/issues/{assignee}")
    suspend fun getIssuesForAssignee(
        @Path("assignee")
        userId: Int
    ): ApiResponse<List<Issue>>

    @POST("/projects/{id}/issues/add")
    suspend fun addIssue(
        @Path("id")
        projectId: Int,
        @Query("issue")
        issue: Issue,
    ): ApiResponse<Issue>

    @GET("/issues/{id}/comments")
    suspend fun getComments(
        @Path("id")
        issueId: Int
    ): ApiResponse<List<Comment>>

    @POST("/issues/{id}/comments/add")
    suspend fun addComment(
        @Path("id")
        issueId: Int,
        @Query("authorId")
        authorId: Int,
        @Query("date")
        date: Long,
        @Query("text")
        text: String,
    )
}
