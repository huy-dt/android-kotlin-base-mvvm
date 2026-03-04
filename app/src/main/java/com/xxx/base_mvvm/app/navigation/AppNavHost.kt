package com.xxx.base_mvvm.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.xxx.base_mvvm.feature.auth.navigation.AUTH_GRAPH_ROUTE
import com.xxx.base_mvvm.feature.auth.navigation.authGraph
import com.xxx.base_mvvm.feature.auth.navigation.navigateToAuth
import com.xxx.base_mvvm.feature.detail.navigation.detailScreen
import com.xxx.base_mvvm.feature.detail.navigation.navigateToDetail
import com.xxx.base_mvvm.feature.home.navigation.HOME_ROUTE
import com.xxx.base_mvvm.feature.home.navigation.homeScreen
import com.xxx.base_mvvm.feature.home.navigation.navigateToHome
import com.xxx.base_mvvm.feature.profile.navigation.navigateToProfile
import com.xxx.base_mvvm.feature.profile.navigation.profileScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AUTH_GRAPH_ROUTE
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // ── Auth (nested graph: login ↔ register navigate local) ───────────
        authGraph(
            navController     = navController,
            onLoginSuccess    = { navController.navigateToHome() },
            onRegisterSuccess = { navController.navigateToHome() }
        )

        // ── Home ───────────────────────────────────────────────────────────
        homeScreen(
            onNavigateToDetail  = { userId -> navController.navigateToDetail(userId) },
            onNavigateToProfile = { navController.navigateToProfile() }
        )

        // ── Detail ─────────────────────────────────────────────────────────
        detailScreen(
            onNavigateBack = { navController.popBackStack() }
        )

        // ── Profile ────────────────────────────────────────────────────────
        profileScreen(
            onLogout       = { navController.navigateToAuth() },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
