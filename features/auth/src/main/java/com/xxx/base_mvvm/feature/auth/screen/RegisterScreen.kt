package com.xxx.base_mvvm.feature.auth.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxx.base_mvvm.core.ui.component.AppButton
import com.xxx.base_mvvm.core.ui.component.AppTextField
import com.xxx.base_mvvm.feature.auth.state.RegisterUiEvent
import com.xxx.base_mvvm.feature.auth.viewmodel.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                RegisterUiEvent.NavigateToHome  -> onRegisterSuccess()
                RegisterUiEvent.NavigateToLogin -> onNavigateToLogin()
                is RegisterUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement   = Arrangement.Center,
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            Text("Đăng ký", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))

            AppTextField(
                value         = uiState.name,
                onValueChange = viewModel::onNameChange,
                label         = "Họ tên",
                isError       = uiState.nameError != null,
                errorMessage  = uiState.nameError
            )
            Spacer(Modifier.height(12.dp))

            AppTextField(
                value         = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label         = "Email",
                isError       = uiState.emailError != null,
                errorMessage  = uiState.emailError
            )
            Spacer(Modifier.height(12.dp))

            AppTextField(
                value                = uiState.password,
                onValueChange        = viewModel::onPasswordChange,
                label                = "Mật khẩu",
                isError              = uiState.passwordError != null,
                errorMessage         = uiState.passwordError,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(12.dp))

            AppTextField(
                value                = uiState.confirmPassword,
                onValueChange        = viewModel::onConfirmPasswordChange,
                label                = "Xác nhận mật khẩu",
                isError              = uiState.confirmPasswordError != null,
                errorMessage         = uiState.confirmPasswordError,
                visualTransformation = PasswordVisualTransformation()
            )

            if (uiState.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(24.dp))
            AppButton(
                text    = "Đăng ký",
                onClick = viewModel::onRegister,
                loading = uiState.isLoading
            )

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = viewModel::onNavigateToLogin) {
                Text("Đã có tài khoản? Đăng nhập")
            }
        }
    }
}
