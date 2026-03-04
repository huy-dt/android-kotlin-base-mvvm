package com.xxx.base_mvvm.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xxx.base_mvvm.feature.detail.screen.DetailScreen

const val DETAIL_USER_ID_ARG = "userId"
const val DETAIL_ROUTE = "detail/{$DETAIL_USER_ID_ARG}"

fun NavController.navigateToDetail(userId: Int) = navigate("detail/$userId")

fun NavGraphBuilder.detailScreen(onNavigateBack: () -> Unit) {
    composable(
        route = DETAIL_ROUTE,
        arguments = listOf(navArgument(DETAIL_USER_ID_ARG) { type = NavType.IntType })
    ) {
        DetailScreen(onNavigateBack = onNavigateBack)
    }
}
