package ru.andvl.bugtracker.presentation.ui.issues

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andvl.bugtracker.model.Issue
import ru.andvl.bugtracker.model.Project
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
}