package com.xxx.base_mvvm.feature.detail.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxx.base_mvvm.core.ui.component.AppTopBar
import com.xxx.base_mvvm.core.ui.component.ErrorScreen
import com.xxx.base_mvvm.core.ui.component.LoadingWheel
import com.xxx.base_mvvm.feature.detail.state.DetailUiEvent
import com.xxx.base_mvvm.feature.detail.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is DetailUiEvent.NavigateBack    -> onNavigateBack()
                is DetailUiEvent.ShowSnackbar    -> { /* handle snackbar */ }
            }
        }
    }

    Scaffold(topBar = { AppTopBar(title = uiState.user?.name ?: "Chi tiết", onNavigateUp = viewModel::onBack) }) { padding ->
        when {
            uiState.isLoading     -> LoadingWheel()
            uiState.error != null -> ErrorScreen(message = uiState.error!!)
            uiState.user != null  -> {
                val user = uiState.user!!
                Column(Modifier.padding(padding).padding(16.dp)) {
                    Text(user.name,  style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(user.email, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
