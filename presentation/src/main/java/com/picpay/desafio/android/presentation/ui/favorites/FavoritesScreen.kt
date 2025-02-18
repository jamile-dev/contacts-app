package com.picpay.desafio.android.presentation.ui.favorites

import UserList
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.ui.components.EmptyFavoritesScreen
import com.picpay.desafio.android.presentation.ui.components.ErrorScreen
import com.picpay.desafio.android.presentation.ui.state.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    navigateToDetails: (String) -> Unit,
) {
    val favoriteUsersUiState by viewModel.favoriteUsersUiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadFavoriteUsers() }

    when (favoriteUsersUiState) {
        is UIState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is UIState.Success -> {
            val users = (favoriteUsersUiState as UIState.Success<List<User>>).data
            if (users.isNotEmpty()) {
                UserList(
                    users = users,
                    onClick = navigateToDetails,
                    onFavoriteClick = { user -> viewModel.toggleFavorite(user.id) },
                )
            } else {
                EmptyFavoritesScreen()
            }
        }

        is UIState.Error -> {
            val exception = (favoriteUsersUiState as UIState.Error).exception.message.toString()
            ErrorScreen(
                message = exception,
                onRetry = { viewModel.loadFavoriteUsers() },
            )
        }
    }
}
