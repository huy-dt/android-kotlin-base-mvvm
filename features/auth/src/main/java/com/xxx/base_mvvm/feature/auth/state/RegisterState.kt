package com.xxx.base_mvvm.feature.auth.state

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val error: String? = null
) {
    val isFormValid get() = name.isNotBlank()
            && email.isNotBlank()
            && password.length >= 6
            && password == confirmPassword
}

sealed class RegisterUiEvent {
    object NavigateToHome  : RegisterUiEvent()
    object NavigateToLogin : RegisterUiEvent()
    data class ShowSnackbar(val message: String) : RegisterUiEvent()
}
