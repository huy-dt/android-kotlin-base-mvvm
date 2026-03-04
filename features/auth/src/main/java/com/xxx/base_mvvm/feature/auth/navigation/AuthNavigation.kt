package com.xxx.base_mvvm.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.xxx.base_mvvm.feature.auth.screen.LoginScreen
import com.xxx.base_mvvm.feature.auth.screen.RegisterScreen

// ── Routes — private trong auth graph, bên ngoài không cần biết ───────────
private const val LOGIN_ROUTE    = "auth/login"
private const val REGISTER_ROUTE = "auth/register"

// ── Graph route — public, app dùng để navigate vào auth ───────────────────
const val AUTH_GRAPH_ROUTE = "auth"

fun NavController.navigateToAuth() = navigate(AUTH_GRAPH_ROUTE) {
    popUpTo(graph.startDestinationId) { inclusive = true }
}

/**
 * Nested NavGraph cho auth.
 * Login ↔ Register navigate local (không lộ route ra AppNavHost).
 * Chỉ 2 callback ra ngoài: onLoginSuccess, onRegisterSuccess.
 */
fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    navigation(
        route            = AUTH_GRAPH_ROUTE,
        startDestination = LOGIN_ROUTE
    ) {
        // ── Login ──────────────────────────────────────────────────────────
        composable(LOGIN_ROUTE) {
            LoginScreen(
                onLoginSuccess       = onLoginSuccess,
                onNavigateToRegister = { navController.navigate(REGISTER_ROUTE) }
            )
        }

        // ── Register ───────────────────────────────────────────────────────
        composable(REGISTER_ROUTE) {
            RegisterScreen(
                onRegisterSuccess = onRegisterSuccess,
                onNavigateToLogin = { navController.popBackStack() }  // quay lại login
            )
        }
    }
}
