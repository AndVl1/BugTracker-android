package ru.andvl.bugtracker.presentation.ui.issues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.Comment
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.User
import ru.andvl.bugtracker.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _isFilterExpanded = MutableStateFlow(false)
    val isFilterExpanded = _isFilterExpanded.asStateFlow()

    private val _issuesList: MutableStateFlow<List<Issue>> =
        MutableStateFlow(ArrayList())
    val issuesList = _issuesList.asStateFlow()

    fun onArrowClicked() {
        _isFilterExpanded.value = !_isFilterExpanded.value
    }

    private fun loadIssuesForUser(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainRepository.loadIssuesForUser(id)
            }
        }
    }

    fun loadIssuesForUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainRepository.loadIssuesForUser()
            }
        }
    }

    fun getIssues() {
        viewModelScope.launch {
            mainRepository.getIssues()
                .collect {
                    _issuesList.emit(it)
                }
        }
    }

    private val _added = MutableStateFlow(false)
    val added = _added.asStateFlow()

    fun addIssue(
        name: String,
        description: String,
        date: Long,
        assigneeId: Int?,
        projectId: Int,
    ) {
        viewModelScope.launch {
            _added.emit(false)
            mainRepository.addIssue(
                name = name,
                description = description,
                date = date,
                assigneeId = assigneeId,
                projectId = projectId,
            )
        }
    }

    private val _selectedIssue: MutableStateFlow<Issue> =
        MutableStateFlow(
            Issue(
                id = -1,
                issueName = "",
                description = "",
                projectId = -1,
                authorId = 1,
            )
        )
    val selectedIssue = _selectedIssue.asStateFlow()
    private val _assignee: MutableStateFlow<User> =
        MutableStateFlow(User(-1, "", ""))
    val assignee = _assignee.asStateFlow()

    fun getIssue(id: Int) {
        viewModelScope.launch {
            mainRepository.getIssue(id)
                .collect{
                    _selectedIssue.emit(it)
                }
        }
    }

    fun getUser(id: Int) {
        viewModelScope.launch {
            mainRepository.getUser(id)
                .suspendOnSuccess {
                    this.data?.let { _assignee.emit(it) }
                }
        }
    }

    private val _commentsList = MutableStateFlow<List<Comment>>(listOf())
    val commentsList = _commentsList.asStateFlow()

    fun updateComments(issueId: Int) {
        viewModelScope.launch {
            _commentsList.emit(mainRepository.getComments(issueId = issueId))
        }
    }

    fun addComment(
        issueId: Int,
        text: String,
    ) {
        viewModelScope.launch {
            mainRepository.addComment(
                issueId = issueId,
                text = text
            )
        }
    }

}
