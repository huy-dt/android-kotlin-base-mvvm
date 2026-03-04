package com.xxx.base_mvvm.feature.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.ui.component.EmptyScreen
import com.xxx.base_mvvm.core.ui.component.ErrorScreen
import com.xxx.base_mvvm.core.ui.component.LoadingWheel
import com.xxx.base_mvvm.feature.home.state.HomeUiEvent
import com.xxx.base_mvvm.feature.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is HomeUiEvent.NavigateToDetail  -> onNavigateToDetail(event.userId)
                is HomeUiEvent.NavigateToProfile -> onNavigateToProfile()
                is HomeUiEvent.ShowSnackbar      -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Trang chủ") },
                actions = {
                    TextButton(onClick = viewModel::onProfileClick) { Text("Hồ sơ") }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Tìm kiếm...") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            when {
                uiState.isLoading          -> LoadingWheel()
                uiState.error != null      -> ErrorScreen(message = uiState.error!!, onRetry = viewModel::onRefresh)
                uiState.users.isEmpty()    -> EmptyScreen()
                else -> UserList(users = uiState.users, onUserClick = viewModel::onUserClick)
            }
        }
    }
}

@Composable
private fun UserList(users: List<User>, onUserClick: (Int) -> Unit) {
    LazyColumn {
        items(users, key = { it.id }) { user ->
            UserCard(user = user, onClick = { onUserClick(user.id) })
            HorizontalDivider()
        }
    }
}

@Composable
private fun UserCard(user: User, onClick: () -> Unit) {
    ListItem(
        modifier      = Modifier.clickable(onClick = onClick),
        headlineContent  = { Text(user.name, style = MaterialTheme.typography.titleMedium) },
        supportingContent = { Text(user.email, style = MaterialTheme.typography.bodyMedium) }
    )
}
