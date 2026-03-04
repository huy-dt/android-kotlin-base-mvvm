package com.xxx.base_mvvm.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.base_mvvm.core.common.result.Result
import com.xxx.base_mvvm.core.domain.usecase.auth.RegisterParams
import com.xxx.base_mvvm.core.domain.usecase.auth.RegisterUseCase
import com.xxx.base_mvvm.feature.auth.state.RegisterUiEvent
import com.xxx.base_mvvm.feature.auth.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameChange(name: String) =
        _uiState.update { it.copy(name = name, nameError = null, error = null) }

    fun onEmailChange(email: String) =
        _uiState.update { it.copy(email = email, emailError = null, error = null) }

    fun onPasswordChange(pw: String) =
        _uiState.update { it.copy(password = pw, passwordError = null, error = null) }

    fun onConfirmPasswordChange(pw: String) =
        _uiState.update { it.copy(confirmPassword = pw, confirmPasswordError = null, error = null) }

    fun onRegister() {
        val s = _uiState.value

        val nameErr    = if (s.name.isBlank()) "Tên không được trống" else null
        val emailErr   = if (s.email.isBlank()) "Email không được trống" else null
        val passErr    = if (s.password.length < 6) "Tối thiểu 6 ký tự" else null
        val confirmErr = if (s.password != s.confirmPassword) "Mật khẩu không khớp" else null

        if (nameErr != null || emailErr != null || passErr != null || confirmErr != null) {
            _uiState.update {
                it.copy(
                    nameError            = nameErr,
                    emailError           = emailErr,
                    passwordError        = passErr,
                    confirmPasswordError = confirmErr
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = registerUseCase(RegisterParams(s.name.trim(), s.email.trim(), s.password))) {
                is Result.Success -> _uiEvent.send(RegisterUiEvent.NavigateToHome)
                is Result.Error   -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                    _uiEvent.send(RegisterUiEvent.ShowSnackbar(result.message))
                }
                is Result.Loading -> Unit
            }
        }
    }

    fun onNavigateToLogin() {
        viewModelScope.launch { _uiEvent.send(RegisterUiEvent.NavigateToLogin) }
    }
}
