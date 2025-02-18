package com.picpay.desafio.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.picpay.desafio.android.presentation.ui.favorites.FavoritesScreen
import com.picpay.desafio.android.presentation.ui.home.HomeScreen
import com.picpay.desafio.android.presentation.ui.userdetail.UserDetailScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavItem.BottomNavItem.Home.route) {
        composable(NavItem.BottomNavItem.Home.route) {
            HomeScreen(navigateToDetails = { userId ->
                navController.navigate(NavItem.UserDetail.createRoute(userId))
            })
        }

        composable(NavItem.BottomNavItem.Favorites.route) {
            FavoritesScreen(navigateToDetails = { userId ->
                navController.navigate(NavItem.UserDetail.createRoute(userId))
            })
        }

        composable(
            route = NavItem.UserDetail.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            UserDetailScreen(
                userId = userId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
