package com.picpay.desafio.android.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.ManageLocalUsersUseCase
import com.picpay.desafio.android.presentation.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val manageLocalUsersUseCase: ManageLocalUsersUseCase,
    private val favoriteUsersUseCase: FavoriteUsersUseCase,
) : ViewModel() {
    private val _favoriteUsersUiState = MutableStateFlow<UIState<List<User>>>(UIState.Loading)
    val favoriteUsersUiState: StateFlow<UIState<List<User>>> = _favoriteUsersUiState

    fun loadFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersUiState.value = UIState.Loading
            val result = manageLocalUsersUseCase.getCachedFavorites()

            _favoriteUsersUiState.value =
                when (result) {
                    is Result.Success -> UIState.Success(result.data)
                    is Result.Error -> UIState.Error(result.exception)
                }
        }
    }

    fun toggleFavorite(userId: Int) {
        viewModelScope.launch {
            val currentFavorites = (_favoriteUsersUiState.value as? UIState.Success)?.data ?: emptyList()
            val user = currentFavorites.find { it.id == userId }

            if (user != null) {
                favoriteUsersUseCase.invoke(userId, isFavorite = false)
            } else {
                favoriteUsersUseCase.invoke(userId, isFavorite = true)
            }

            loadFavoriteUsers()
        }
    }
}
