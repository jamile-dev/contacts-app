package com.picpay.desafio.android.presentation.ui.favorites

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.ManageLocalUsersUseCase
import com.picpay.desafio.android.presentation.core.BaseViewModelTest
import com.picpay.desafio.android.presentation.ui.state.UIState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest : BaseViewModelTest() {
    private val manageLocalUsersUseCase: ManageLocalUsersUseCase = mockk()
    private val favoriteUsersUseCase: FavoriteUsersUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        viewModel = FavoritesViewModel(manageLocalUsersUseCase, favoriteUsersUseCase)
    }

    @Test
    fun `loadFavoriteUsers should emit Success state with data when use case returns data`() =
        runTest {
            // Arrange
            val favoriteUsers =
                listOf(
                    User(
                        id = 1,
                        name = "John Doe",
                        username = "johndoe",
                        img = "img_url",
                        isFavorite = true,
                    ),
                )
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    favoriteUsers,
                )

            // Act
            viewModel.loadFavoriteUsers()
            advanceUntilIdle()

            // Assert
            val state = viewModel.favoriteUsersUiState.value
            assertTrue(state is UIState.Success)
            assertEquals(favoriteUsers, (state as UIState.Success).data)
        }

    @Test
    fun `loadFavoriteUsers should emit Error state when use case returns error`() =
        runTest {
            // Arrange
            val exception = Exception("Error loading favorites")
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns Result.Error(exception)

            // Act
            viewModel.loadFavoriteUsers()
            advanceUntilIdle()

            // Assert
            val state = viewModel.favoriteUsersUiState.value
            assertTrue(state is UIState.Error)
            assertEquals(exception, (state as UIState.Error).exception)
        }

    @Test
    fun `toggleFavorite should remove user from favorites when user is already favorite`() =
        runTest {
            // Arrange
            val user =
                User(
                    id = 1,
                    name = "John Doe",
                    username = "johndoe",
                    img = "img_url",
                    isFavorite = true,
                )
            val favoriteUsers = listOf(user)
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    favoriteUsers,
                )
            coEvery { favoriteUsersUseCase.invoke(user.id, false) } returns Result.Success(Unit)

            // Act
            viewModel.loadFavoriteUsers()
            advanceUntilIdle()
            viewModel.toggleFavorite(user.id)
            advanceUntilIdle()

            // Assert
            coVerify { favoriteUsersUseCase.invoke(user.id, false) }
        }

    @Test
    fun `toggleFavorite should add user to favorites when user is not favorite`() =
        runTest {
            // Arrange
            val user =
                User(
                    id = 1,
                    name = "John Doe",
                    username = "johndoe",
                    img = "img_url",
                    isFavorite = false,
                )
            val favoriteUsers = emptyList<User>()
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    favoriteUsers,
                )
            coEvery { favoriteUsersUseCase.invoke(user.id, true) } returns Result.Success(Unit)

            // Act
            viewModel.loadFavoriteUsers()
            advanceUntilIdle()
            viewModel.toggleFavorite(user.id)
            advanceUntilIdle()

            // Assert
            coVerify { favoriteUsersUseCase.invoke(user.id, true) }
        }
}
