package com.xxx.base_mvvm.feature.auth.state

data class LoginUiState(
    val email: String = "admin@admin.com",
    val password: String = "123456",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val error: String? = null
) {
    val isFormValid get() = email.isNotBlank() && password.length >= 6
}

sealed class LoginUiEvent {
    object NavigateToHome     : LoginUiEvent()
    object NavigateToRegister : LoginUiEvent()
    data class ShowSnackbar(val message: String) : LoginUiEvent()
}
