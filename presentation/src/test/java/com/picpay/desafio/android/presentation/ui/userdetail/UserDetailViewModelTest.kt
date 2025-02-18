package com.picpay.desafio.android.presentation.ui.userdetail

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.GetUserByIdUseCase
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
class UserDetailViewModelTest : BaseViewModelTest() {
    private val getUserByIdUseCase: GetUserByIdUseCase = mockk()
    private val favoriteUsersUseCase: FavoriteUsersUseCase = mockk()
    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setUp() {
        viewModel = UserDetailViewModel(getUserByIdUseCase, favoriteUsersUseCase)
    }

    @Test
    fun `loadUserById should emit Success state with user data when use case returns data`() =
        runTest {
            // Arrange
            val userId = 1
            val user = User(id = userId, name = "John Doe", username = "johndoe", img = "img_url")
            coEvery { getUserByIdUseCase(userId) } returns Result.Success(user)

            // Act
            viewModel.loadUserById(userId)
            advanceUntilIdle()

            // Assert
            val state = viewModel.userState.value
            assertTrue(state is UIState.Success)
            assertEquals(user, (state as UIState.Success).data)
            coVerify { getUserByIdUseCase(userId) }
        }

    @Test
    fun `loadUserById should emit Error state when use case returns error`() =
        runTest {
            // Arrange
            val userId = 1
            val exception = Exception("User not found")
            coEvery { getUserByIdUseCase(userId) } returns Result.Error(exception)

            // Act
            viewModel.loadUserById(userId)
            advanceUntilIdle()

            // Assert
            val state = viewModel.userState.value
            assertTrue(state is UIState.Error)
            assertEquals(exception, (state as UIState.Error).exception)
            coVerify { getUserByIdUseCase(userId) }
        }

    @Test
    fun `toggleFavorite should update user favorite status`() =
        runTest {
            // Arrange
            val userId = 1
            val user =
                User(
                    id = userId,
                    name = "John Doe",
                    username = "johndoe",
                    img = "img_url",
                    isFavorite = false,
                )
            val updatedUser = user.copy(isFavorite = true)
            coEvery { getUserByIdUseCase(userId) } returns Result.Success(user)
            coEvery { favoriteUsersUseCase.invoke(userId, true) } returns Result.Success(Unit)

            // Act
            viewModel.loadUserById(userId)
            advanceUntilIdle()
            viewModel.toggleFavorite()
            advanceUntilIdle()

            // Assert
            val state = viewModel.userState.value
            assertTrue(state is UIState.Success)
            assertEquals(updatedUser, (state as UIState.Success).data)
            coVerify { favoriteUsersUseCase.invoke(userId, true) }
        }

    @Test
    fun `toggleFavorite should emit Error state when favoriteUsersUseCase returns error`() =
        runTest {
            // Arrange
            val userId = 1
            val user =
                User(
                    id = userId,
                    name = "John Doe",
                    username = "johndoe",
                    img = "img_url",
                    isFavorite = false,
                )
            val exception = Exception("Failed to update favorite status")
            coEvery { getUserByIdUseCase(userId) } returns Result.Success(user)
            coEvery { favoriteUsersUseCase.invoke(userId, true) } returns Result.Error(exception)

            // Act
            viewModel.loadUserById(userId)
            advanceUntilIdle()
            viewModel.toggleFavorite()
            advanceUntilIdle()

            // Assert
            val state = viewModel.userState.value
            assertTrue(state is UIState.Error)
            assertEquals(exception, (state as UIState.Error).exception)
            coVerify { favoriteUsersUseCase.invoke(userId, true) }
        }
}
