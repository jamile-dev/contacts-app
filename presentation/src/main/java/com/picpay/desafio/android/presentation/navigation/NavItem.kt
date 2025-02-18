package com.picpay.desafio.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
) {
    sealed class BottomNavItem(
        route: String,
        val icon: ImageVector,
        val title: String,
    ) : NavItem(route) {
        data object Home : BottomNavItem("home", Icons.Filled.Home, "Home")

        data object Favorites : BottomNavItem("favorites", Icons.Filled.Favorite, "Favorites")
    }

    data object UserDetail : NavItem("userDetail/{userId}") {
        fun createRoute(userId: String) = "userDetail/$userId"
    }
}
