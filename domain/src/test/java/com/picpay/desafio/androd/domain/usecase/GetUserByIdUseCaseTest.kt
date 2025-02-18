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

class GetUserByIdUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = GetUserByIdUseCase(userRepository)

    @Test
    fun `invoke should return Success with user when repository returns user`() =
        runTest {
            // Arrange
            val userId = 1
            val expectedUser =
                User(id = userId, name = "John Doe", username = "johndoe", img = "img_url")
            coEvery { userRepository.getUserById(userId) } returns Result.Success(expectedUser)

            // Act
            val result = useCase(userId)

            // Assert
            assertEquals(Result.Success(expectedUser), result)
            coVerify { userRepository.getUserById(userId) }
        }

    @Test
    fun `invoke should return Error when repository throws exception`() =
        runTest {
            // Arrange
            val userId = 1
            val exception = Exception("User not found")
            coEvery { userRepository.getUserById(userId) } returns Result.Error(exception)

            // Act
            val result = useCase(userId)

            // Assert
            assertEquals(Result.Error(exception), result)
            coVerify { userRepository.getUserById(userId) }
        }
}
