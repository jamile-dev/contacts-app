package com.picpay.desafio.android.presentation.ui.userdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.R
import com.picpay.desafio.android.presentation.ui.state.UIState
import com.picpay.desafio.android.presentation.ui.theme.AppColors
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    onBack: () -> Unit,
    userDetailViewModel: UserDetailViewModel = koinViewModel(),
) {
    val userState by userDetailViewModel.userState.collectAsState()

    LaunchedEffect(userId) {
        userDetailViewModel.loadUserById(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.user_details),
                        color = AppColors.Blue,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.icon_back),
                            tint = AppColors.Black,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (userState) {
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = AppColors.Blue)
                    }
                }

                is UIState.Error -> {
                    val errorMessage = (userState as UIState.Error).exception.message.toString()
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.error, errorMessage),
                            color = AppColors.Red,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                }

                is UIState.Success -> {
                    val user = (userState as UIState.Success<User>).data
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HeaderSection(user)
                        Spacer(modifier = Modifier.height(24.dp))
                        FavoriteStatus(
                            user = user,
                            onFavoriteClick = { },
                        )
                    }
                }
            }
        }
    }
}
