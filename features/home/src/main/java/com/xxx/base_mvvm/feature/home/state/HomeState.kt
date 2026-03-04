package com.xxx.base_mvvm.feature.home.state

import com.xxx.base_mvvm.core.domain.model.User

data class HomeUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false
)

sealed class HomeUiEvent {
    data class NavigateToDetail(val userId: Int) : HomeUiEvent()
    data class ShowSnackbar(val message: String) : HomeUiEvent()
    object NavigateToProfile : HomeUiEvent()
}
