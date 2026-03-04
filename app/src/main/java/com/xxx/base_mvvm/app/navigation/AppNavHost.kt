package com.xxx.base_mvvm.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.xxx.base_mvvm.feature.auth.navigation.AUTH_ROUTE
import com.xxx.base_mvvm.feature.auth.navigation.authScreen
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
    startDestination: String = AUTH_ROUTE
) {
    NavHost(navController = navController, startDestination = startDestination) {

        authScreen(
            onLoginSuccess = { navController.navigateToHome() }
        )

        homeScreen(
            onNavigateToDetail  = { userId -> navController.navigateToDetail(userId) },
            onNavigateToProfile = { navController.navigateToProfile() }
        )

        detailScreen(
            onNavigateBack = { navController.popBackStack() }
        )

        profileScreen(
            onLogout       = { navController.navigateToAuth() },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
