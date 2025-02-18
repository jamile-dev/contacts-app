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

class GetUsersUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = GetUsersUseCase(userRepository)

    @Test
    fun `invoke should return Success with users when repository returns users`() =
        runTest {
            // Arrange
            val expectedUsers =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            coEvery { userRepository.getUsers() } returns Result.Success(expectedUsers)

            // Act
            val result = useCase()

            // Assert
            assertEquals(Result.Success(expectedUsers), result)
            coVerify { userRepository.getUsers() }
        }

    @Test
    fun `invoke should return Error when repository throws exception`() =
        runTest {
            // Arrange
            val exception = Exception("Network error")
            coEvery { userRepository.getUsers() } returns Result.Error(exception)

            // Act
            val result = useCase()

            // Assert
            assertEquals(Result.Error(exception), result)
            coVerify { userRepository.getUsers() }
        }
}
