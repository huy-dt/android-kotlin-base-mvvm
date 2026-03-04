package com.xxx.base_mvvm.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xxx.base_mvvm.feature.auth.screen.LoginScreen

const val AUTH_ROUTE = "auth"

fun NavController.navigateToAuth() = navigate(AUTH_ROUTE) {
    popUpTo(graph.startDestinationId) { inclusive = true }
}

fun NavGraphBuilder.authScreen(onLoginSuccess: () -> Unit) {
    composable(route = AUTH_ROUTE) {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
