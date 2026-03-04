package com.xxx.base_mvvm.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xxx.base_mvvm.feature.home.screen.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome() = navigate(HOME_ROUTE) {
    popUpTo(graph.startDestinationId) { inclusive = true }
}

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onNavigateToDetail  = onNavigateToDetail,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}
