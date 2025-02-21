package com.picpay.desafio.android.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.FilterUsersUseCase
import com.picpay.desafio.androd.domain.usecase.GetUsersUseCase
import com.picpay.desafio.androd.domain.usecase.ManageLocalUsersUseCase
import com.picpay.desafio.android.presentation.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val favoriteUsersUseCase: FavoriteUsersUseCase,
    private val manageLocalUsersUseCase: ManageLocalUsersUseCase,
    private val filterUsersUseCase: FilterUsersUseCase,
) : ViewModel() {
    private val _usersUiState = MutableStateFlow<UIState<List<User>>>(UIState.Loading)
    val usersUiState: StateFlow<UIState<List<User>>> = _usersUiState

    private val favoritesUiState = MutableStateFlow<UIState<Result<List<User>>>>(UIState.Loading)

    private var cachedUsers: List<User> = emptyList()

    fun loadUsers() {
        viewModelScope.launch {
            when (val result = getUsersUseCase.invoke()) {
                is Result.Success -> {
                    val users = result.data
                    cachedUsers = users
                    manageLocalUsersUseCase.cacheUsers(users)
                    when (val favoriteResult = manageLocalUsersUseCase.getCachedFavorites()) {
                        is Result.Success -> {
                            favoritesUiState.value = UIState.Success(favoriteResult)
                        }

                        is Result.Error -> {
                            favoritesUiState.value = UIState.Error(favoriteResult.exception)
                        }
                    }
                    _usersUiState.value = UIState.Success(users)
                }

                is Result.Error -> {
                    when (val cachedResult = manageLocalUsersUseCase.getCachedUsers()) {
                        is Result.Success -> {
                            _usersUiState.value = UIState.Success(cachedResult.data)
                        }

                        is Result.Error -> {
                            _usersUiState.value = UIState.Error(cachedResult.exception)
                        }
                    }
                }
            }
        }
    }

    fun toggleFavorite(userId: Int) {
        viewModelScope.launch {
            val currentList = (_usersUiState.value as? UIState.Success)?.data ?: return@launch
            val currentUser = currentList.firstOrNull { it.id == userId } ?: return@launch
            val newFavoriteStatus = !currentUser.isFavorite

            when (val result = favoriteUsersUseCase.invoke(userId, newFavoriteStatus)) {
                is Result.Success -> {
                    cachedUsers =
                        cachedUsers.map { user ->
                            if (user.id == userId) user.copy(isFavorite = newFavoriteStatus) else user
                        }
                    val updatedList =
                        currentList.map { user ->
                            if (user.id == userId) user.copy(isFavorite = newFavoriteStatus) else user
                        }
                    _usersUiState.value = UIState.Success(updatedList)
                }

                is Result.Error -> {
                    _usersUiState.value = UIState.Error(result.exception)
                }
            }
        }
    }

    fun searchUsers(query: String) {
        val filteredUsers = filterUsersUseCase.filterUsers(cachedUsers, query)
        _usersUiState.value = UIState.Success(filteredUsers)
    }
}
