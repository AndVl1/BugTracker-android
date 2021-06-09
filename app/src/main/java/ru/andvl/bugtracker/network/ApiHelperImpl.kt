package ru.andvl.bugtracker.network

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import ru.andvl.bugtracker.model.Comment
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.LoginUser
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    // override suspend fun getUser(): ApiResponse<User> = apiService.getUserData()
    override suspend fun getUser(userId: Int): ApiResponse<User> =
        apiService.getUserData(userId)

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

    override suspend fun addProject(
        userId: Int,
        projectName: String,
        projectDescription: String
    ): ApiResponse<Project> =
        apiService.addProject(
            userId = userId,
            projectName = projectName,
            projectDescription = projectDescription,
        )

    override suspend fun getIssuesForProject(projectId: Int): ApiResponse<List<Issue>> =
        apiService.getIssuesForProject(projectId)

    override suspend fun getIssuesForAssignee(userId: Int): ApiResponse<List<Issue>> =
        apiService.getIssuesForAssignee(userId)

    override suspend fun addIssue(issue: Issue): ApiResponse<Issue> =
        apiService.addIssue(issue.projectId, issue)

    override suspend fun getComments(issueId: Int): ApiResponse<List<Comment>> =
        apiService.getComments(issueId)

    override suspend fun addComment(issueId: Int, authorId: Int, date: Long, text: String) {
        apiService.addComment(
            issueId = issueId,
            authorId = authorId,
            date = date,
            text = text,
        )
    }
}
