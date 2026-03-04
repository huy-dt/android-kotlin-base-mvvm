package com.xxx.base_mvvm.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xxx.base_mvvm.feature.profile.screen.ProfileScreen

const val PROFILE_ROUTE = "profile"

fun NavController.navigateToProfile() = navigate(PROFILE_ROUTE)

fun NavGraphBuilder.profileScreen(
    onLogout: () -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(route = PROFILE_ROUTE) {
        ProfileScreen(onLogout = onLogout, onNavigateBack = onNavigateBack)
    }
}
