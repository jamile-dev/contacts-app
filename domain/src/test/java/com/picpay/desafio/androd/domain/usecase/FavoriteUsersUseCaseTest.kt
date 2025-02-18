package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FavoriteUsersUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = FavoriteUsersUseCase(userRepository)

    @Test
    fun `invoke should return Success when repository toggles favorite successfully`() =
        runTest {
            // Arrange
            val userId = 1
            val isFavorite = true
            val expectedResult = Result.Success(Unit)
            coEvery { userRepository.toggleFavoriteUser(userId, isFavorite) } returns expectedResult

            // Act
            val result = useCase(userId, isFavorite)

            // Assert
            assertEquals(Result.Success(Unit), result)
            coVerify { userRepository.toggleFavoriteUser(userId, isFavorite) }
        }

    @Test
    fun `invoke should return Error when repository fails to toggle favorite`() =
        runTest {
            // Arrange
            val userId = 1
            val isFavorite = false
            val exception = Exception("Failed to toggle favorite")
            val expectedResult = Result.Error(exception)
            coEvery { userRepository.toggleFavoriteUser(userId, isFavorite) } returns expectedResult

            // Act
            val result = useCase(userId, isFavorite)

            // Assert
            assertEquals(Result.Error(exception), result)
            coVerify { userRepository.toggleFavoriteUser(userId, isFavorite) }
        }
}
