package com.picpay.desafio.android.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.picpay.desafio.android.presentation.navigation.BottomNavigationBar
import com.picpay.desafio.android.presentation.navigation.Navigation
import com.picpay.desafio.android.presentation.ui.theme.ContactsAppTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    ContactsAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = { paddingValues ->
                Box(
                    modifier =
                        Modifier
                            .padding(paddingValues)
                            .statusBarsPadding(),
                ) {
                    Navigation(navController)
                }
            },
        )
    }
}
