package com.xxx.base_mvvm.feature.profile.state

import com.xxx.base_mvvm.core.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ProfileUiEvent {
    object Logout : ProfileUiEvent()
    object NavigateBack : ProfileUiEvent()
}
