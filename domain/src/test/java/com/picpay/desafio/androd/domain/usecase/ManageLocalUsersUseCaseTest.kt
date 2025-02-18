package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ManageLocalUsersUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = ManageLocalUsersUseCase(userRepository)

    @Test
    fun `cacheUsers should call saveUsers on repository`() =
        runTest {
            // Arrange
            val users =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            coEvery { userRepository.saveUsers(users) } returns Unit

            // Act
            useCase.cacheUsers(users)

            // Assert
            coVerify { userRepository.saveUsers(users) }
        }

    @Test
    fun `getCachedUsers should return Success with users when repository returns users`() =
        runTest {
            // Arrange
            val expectedUsers =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            coEvery { userRepository.getUsers() } returns Result.Success(expectedUsers)

            // Act
            val result = useCase.getCachedUsers()

            // Assert
            assertEquals(Result.Success(expectedUsers), result)
            coVerify { userRepository.getUsers() }
        }

    @Test
    fun `getCachedFavorites should return Success with favorite users when repository returns favorites`() =
        runTest {
            // Arrange
            val expectedFavorites =
                listOf(
                    User(
                        id = 1,
                        name = "John Doe",
                        username = "johndoe",
                        img = "img_url",
                        isFavorite = true,
                    ),
                )
            coEvery { userRepository.getFavoriteUsers() } returns Result.Success(expectedFavorites)

            // Act
            val result = useCase.getCachedFavorites()

            // Assert
            assertEquals(Result.Success(expectedFavorites), result)
            coVerify { userRepository.getFavoriteUsers() }
        }
}
