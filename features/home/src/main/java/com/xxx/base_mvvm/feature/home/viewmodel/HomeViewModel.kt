package com.xxx.base_mvvm.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.base_mvvm.core.common.result.Result
import com.xxx.base_mvvm.core.domain.usecase.user.GetUsersUseCase
import com.xxx.base_mvvm.feature.home.state.HomeUiEvent
import com.xxx.base_mvvm.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { loadUsers() }

    fun onSearchQueryChange(query: String) = _uiState.update { it.copy(searchQuery = query) }

    fun onUserClick(userId: Int) {
        viewModelScope.launch { _uiEvent.send(HomeUiEvent.NavigateToDetail(userId)) }
    }

    fun onProfileClick() {
        viewModelScope.launch { _uiEvent.send(HomeUiEvent.NavigateToProfile) }
    }

    fun onRefresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        loadUsers()
    }

    private fun loadUsers() {
        getUsersUseCase().onEach { result ->
            _uiState.update {
                when (result) {
                    is Result.Loading -> it.copy(isLoading = true, isRefreshing = false)
                    is Result.Success -> it.copy(isLoading = false, isRefreshing = false, users = result.data, error = null)
                    is Result.Error   -> it.copy(isLoading = false, isRefreshing = false, error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}
