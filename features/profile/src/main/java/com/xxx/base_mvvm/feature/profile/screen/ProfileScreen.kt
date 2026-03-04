package com.xxx.base_mvvm.feature.profile.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxx.base_mvvm.core.ui.component.AppButton
import com.xxx.base_mvvm.core.ui.component.AppTopBar
import com.xxx.base_mvvm.feature.profile.state.ProfileUiEvent
import com.xxx.base_mvvm.feature.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                ProfileUiEvent.Logout        -> onLogout()
                ProfileUiEvent.NavigateBack  -> onNavigateBack()
            }
        }
    }

    Scaffold(topBar = { AppTopBar(title = "Hồ sơ", onNavigateUp = viewModel::onBack) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Text(uiState.user?.name ?: "Người dùng", style = MaterialTheme.typography.titleLarge)
            Text(uiState.user?.email ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.weight(1f))
            AppButton(text = "Đăng xuất", onClick = viewModel::onLogout)
            Spacer(Modifier.height(16.dp))
        }
    }
}
