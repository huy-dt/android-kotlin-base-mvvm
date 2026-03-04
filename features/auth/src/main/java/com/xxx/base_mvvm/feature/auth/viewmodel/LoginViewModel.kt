package com.xxx.base_mvvm.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.base_mvvm.core.common.result.Result
import com.xxx.base_mvvm.core.domain.usecase.auth.LoginParams
import com.xxx.base_mvvm.core.domain.usecase.auth.LoginUseCase
import com.xxx.base_mvvm.feature.auth.state.LoginUiEvent
import com.xxx.base_mvvm.feature.auth.state.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChange(email: String) = _uiState.update { it.copy(email = email, emailError = null) }
    fun onPasswordChange(pw: String)  = _uiState.update { it.copy(password = pw, passwordError = null) }

    fun onLogin() {
        val state = _uiState.value
        if (!state.isFormValid) {
            _uiState.update {
                it.copy(
                    emailError    = if (it.email.isBlank()) "Email không được trống" else null,
                    passwordError = if (it.password.length < 6) "Mật khẩu tối thiểu 6 ký tự" else null
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = loginUseCase(LoginParams(state.email, state.password))) {
                is Result.Success -> _uiEvent.send(LoginUiEvent.NavigateToHome)
                is Result.Error   -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                    _uiEvent.send(LoginUiEvent.ShowSnackbar(result.message))
                }
                is Result.Loading -> Unit
            }
        }
    }
}
