package com.picpay.desafio.android.presentation.ui.home

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.FilterUsersUseCase
import com.picpay.desafio.androd.domain.usecase.GetUsersUseCase
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
class HomeViewModelTest : BaseViewModelTest() {
    private val getUsersUseCase: GetUsersUseCase = mockk()
    private val favoriteUsersUseCase: FavoriteUsersUseCase = mockk()
    private val manageLocalUsersUseCase: ManageLocalUsersUseCase = mockk()
    private val filterUsersUseCase: FilterUsersUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel =
            HomeViewModel(
                getUsersUseCase,
                favoriteUsersUseCase,
                manageLocalUsersUseCase,
                filterUsersUseCase,
            )
    }

    @Test
    fun `loadUsers should emit Success state with users when getUsersUseCase returns data`() =
        runTest {
            // Arrange
            val users =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            coEvery { getUsersUseCase.invoke() } returns Result.Success(users)
            coEvery { manageLocalUsersUseCase.cacheUsers(users) } returns Unit
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    emptyList(),
                )

            // Act
            viewModel.loadUsers()
            advanceUntilIdle()

            // Assert
            val state = viewModel.usersUiState.value
            assertTrue(state is UIState.Success)
            assertEquals(users, (state as UIState.Success).data)
            coVerify { manageLocalUsersUseCase.cacheUsers(users) }
        }

    @Test
    fun `loadUsers should emit Error state when getUsersUseCase returns error and no cached users`() =
        runTest {
            // Arrange
            val exception = Exception("Network error")
            coEvery { getUsersUseCase.invoke() } returns Result.Error(exception)
            coEvery { manageLocalUsersUseCase.getCachedUsers() } returns Result.Error(exception)

            // Act
            viewModel.loadUsers()
            advanceUntilIdle()

            // Assert
            val state = viewModel.usersUiState.value
            assertTrue(state is UIState.Error)
            assertEquals(exception, (state as UIState.Error).exception)
        }

    @Test
    fun `toggleFavorite should update user favorite status`() =
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
            val updatedUser = user.copy(isFavorite = true)
            val users = listOf(user)
            coEvery { getUsersUseCase.invoke() } returns Result.Success(users)
            coEvery { manageLocalUsersUseCase.cacheUsers(users) } returns Unit
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    emptyList(),
                )
            coEvery { favoriteUsersUseCase.invoke(user.id, true) } returns Result.Success(Unit)

            // Act
            viewModel.loadUsers()
            advanceUntilIdle()
            viewModel.toggleFavorite(user.id)
            advanceUntilIdle()

            // Assert
            val state = viewModel.usersUiState.value
            assertTrue(state is UIState.Success)
            assertEquals(listOf(updatedUser), (state as UIState.Success).data)
            coVerify { favoriteUsersUseCase.invoke(user.id, true) }
        }

    @Test
    fun `searchUsers should filter users based on query`() =
        runTest {
            // Arrange
            val user1 = User(id = 1, name = "John Doe", username = "johndoe", img = "img_url")
            val user2 = User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url")
            val users = listOf(user1, user2)
            val query = "Jane"
            coEvery { getUsersUseCase.invoke() } returns Result.Success(users)
            coEvery { manageLocalUsersUseCase.cacheUsers(users) } returns Unit
            coEvery { manageLocalUsersUseCase.getCachedFavorites() } returns
                Result.Success(
                    emptyList(),
                )
            coEvery { filterUsersUseCase.filterUsers(users, query) } returns listOf(user2)

            // Act
            viewModel.loadUsers()
            advanceUntilIdle()
            viewModel.searchUsers(query)

            // Assert
            val state = viewModel.usersUiState.value
            assertTrue(state is UIState.Success)
            assertEquals(listOf(user2), (state as UIState.Success).data)
            coVerify { filterUsersUseCase.filterUsers(users, query) }
        }
}
