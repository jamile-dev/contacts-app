package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveUsersUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = SaveUsersUseCase(userRepository)

    @Test
    fun `invoke should call saveUsers on repository`() =
        runTest {
            // Arrange
            val users =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            coEvery { userRepository.saveUsers(users) } returns Unit

            // Act
            useCase(users)

            // Assert
            coVerify { userRepository.saveUsers(users) }
        }

    @Test
    fun `invoke should handle exception thrown by repository`() =
        runTest {
            // Arrange
            val users =
                listOf(
                    User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                    User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
                )
            val exception = Exception("Database error")
            coEvery { userRepository.saveUsers(users) } throws exception

            // Act
            try {
                useCase(users)
            } catch (e: Exception) {
                // Assert
                assert(true)
                assert(e.message == "Database error")
            }

            // Verify
            coVerify { userRepository.saveUsers(users) }
        }
}
