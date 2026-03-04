package com.xxx.base_mvvm.core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/* ─── Loading ─────────────────────────────────────────────────────────────── */
@Composable
fun LoadingWheel(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/* ─── Error ───────────────────────────────────────────────────────────────── */
@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        if (onRetry != null) {
            Spacer(Modifier.height(16.dp))
            Button(onClick = onRetry) { Text("Thử lại") }
        }
    }
}

/* ─── Empty ───────────────────────────────────────────────────────────────── */
@Composable
fun EmptyScreen(
    message: String = "Không có dữ liệu",
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
    }
}

/* ─── AppButton ───────────────────────────────────────────────────────────── */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Button(
        onClick  = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        enabled  = enabled && !loading
    ) {
        if (loading)
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        else
            Text(text)
    }
}

/* ─── AppTextField ────────────────────────────────────────────────────────── */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier) {
        OutlinedTextField(
            value                = value,
            onValueChange        = onValueChange,
            label                = { Text(label) },
            isError              = isError,
            singleLine           = singleLine,
            visualTransformation = visualTransformation,
            modifier             = Modifier.fillMaxWidth()
        )
        if (isError && errorMessage != null) {
            Text(
                text     = errorMessage,
                color    = MaterialTheme.colorScheme.error,
                style    = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

/* ─── AppTopBar ───────────────────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null
) {
    TopAppBar(
        title          = { Text(title) },
        modifier       = modifier,
        navigationIcon = {
            if (onNavigateUp != null) {
                IconButton(onClick = onNavigateUp) { Text("←") }
            }
        }
    )
}
