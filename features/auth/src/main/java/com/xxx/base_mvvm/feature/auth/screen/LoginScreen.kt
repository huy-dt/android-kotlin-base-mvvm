package com.xxx.base_mvvm.feature.auth.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxx.base_mvvm.core.ui.component.AppButton
import com.xxx.base_mvvm.core.ui.component.AppTextField
import com.xxx.base_mvvm.feature.auth.viewmodel.LoginViewModel
import com.xxx.base_mvvm.feature.auth.state.LoginUiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is LoginUiEvent.NavigateToHome   -> onLoginSuccess()
                is LoginUiEvent.ShowSnackbar     -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Đăng nhập", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))

            AppTextField(
                value         = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label         = "Email",
                isError       = uiState.emailError != null,
                errorMessage  = uiState.emailError
            )
            Spacer(Modifier.height(16.dp))

            AppTextField(
                value         = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label         = "Mật khẩu",
                isError       = uiState.passwordError != null,
                errorMessage  = uiState.passwordError
            )
            Spacer(Modifier.height(24.dp))

            AppButton(
                text    = "Đăng nhập",
                onClick = viewModel::onLogin,
                enabled = uiState.isFormValid,
                loading = uiState.isLoading
            )
        }
    }
}
