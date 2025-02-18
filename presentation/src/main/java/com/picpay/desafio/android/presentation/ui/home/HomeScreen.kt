package com.picpay.desafio.android.presentation.ui.home

import UserList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.presentation.ui.components.ErrorScreen
import com.picpay.desafio.android.presentation.ui.components.HomeHeader
import com.picpay.desafio.android.presentation.ui.components.SearchTextField
import com.picpay.desafio.android.presentation.ui.state.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetails: (String) -> Unit,
) {
    val usersUiState by viewModel.usersUiState.collectAsState()
    val searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.loadUsers() }

    val onSearch: (String) -> Unit = { query ->
        viewModel.searchUsers(query)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HomeHeader()

        SearchTextField(
            query = remember { mutableStateOf(searchQuery) },
            onSearch = onSearch,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )

        when (val usersState = usersUiState) {
            is UIState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val users = usersState.data
                val filteredUsers =
                    if (searchQuery.isEmpty()) {
                        users
                    } else {
                        users.filter { it.name.contains(searchQuery, ignoreCase = true) }
                    }
                UserList(
                    users = filteredUsers,
                    onClick = navigateToDetails,
                    onFavoriteClick = { user -> viewModel.toggleFavorite(user.id) },
                )
            }

            is UIState.Error -> {
                val exception = usersState.exception
                ErrorScreen(
                    message = exception.localizedMessage ?: "",
                    onRetry = { viewModel.loadUsers() },
                )
            }
        }
    }
}
