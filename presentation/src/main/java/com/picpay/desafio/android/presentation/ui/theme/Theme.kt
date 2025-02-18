package com.picpay.desafio.android.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.picpay.desafio.android.presentation.ui.theme.AppColors.Black
import com.picpay.desafio.android.presentation.ui.theme.AppColors.Green
import com.picpay.desafio.android.presentation.ui.theme.AppColors.Red
import com.picpay.desafio.android.presentation.ui.theme.AppColors.White

object AppColors {
    val Blue = Color(0xFF00B5E2)
    val Green = Color(0xFF00D187)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val Red = Color(0xFFEA4335)
}

private val LightColorScheme =
    lightColorScheme(
        primary = Green,
        secondary = Green,
        background = White,
        surface = White,
        error = Red,
        onPrimary = White,
        onSecondary = White,
        onBackground = Black,
        onSurface = Black,
        onError = White,
    )

@Composable
fun ContactsAppTheme(content: @Composable () -> Unit) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
