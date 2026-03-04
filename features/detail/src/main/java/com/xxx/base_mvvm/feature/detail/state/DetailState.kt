package com.xxx.base_mvvm.feature.detail.state

import com.xxx.base_mvvm.core.domain.model.User

data class DetailUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class DetailUiEvent {
    object NavigateBack : DetailUiEvent()
    data class ShowSnackbar(val message: String) : DetailUiEvent()
}
