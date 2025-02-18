package com.picpay.desafio.android.data

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.data.database.UserDAO
import com.picpay.desafio.android.data.database.UserEntity
import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.mapper.toEntity
import com.picpay.desafio.android.data.model.UserDTO
import com.picpay.desafio.android.data.remote.api.UserApiService
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {
    private val userApiService = mockk<UserApiService>()
    private val userDao = mockk<UserDAO>()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = UserRepositoryImpl(userApiService, userDao, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers returns success when API call succeeds`() =
        runTest {
            // Arrange
            val userDTOs =
                listOf(
                    UserDTO(id = 1, name = "John", username = "john123", img = "img.jpg"),
                    UserDTO(id = 2, name = "Jane", username = "jane456", img = "img2.jpg"),
                )
            val users = userDTOs.map { it.toDomain() }

            coEvery { userApiService.getContacts() } returns userDTOs
            coEvery { userDao.getAllUsers() } returns emptyList()
            coEvery { userDao.insertUsers(any()) } returns Unit

            // Act
            val result = userRepository.getUsers()

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(2, (result as Result.Success).data.size)
            coVerify { userApiService.getContacts() }
            coVerify { userDao.insertUsers(any()) }
        }

    @Test
    fun `getUsers returns local data when API call fails`() =
        runTest {
            // Arrange
            val exception = Exception("API Error")
            val localUsers =
                listOf(
                    UserEntity(
                        id = 1,
                        name = "John",
                        username = "john123",
                        img = "img.jpg",
                        isFavorite = false,
                    ),
                    UserEntity(
                        id = 2,
                        name = "Jane",
                        username = "jane456",
                        img = "img2.jpg",
                        isFavorite = true,
                    ),
                )

            coEvery { userApiService.getContacts() } throws exception
            coEvery { userDao.getAllUsers() } returns localUsers

            // Act
            val result = userRepository.getUsers()

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(2, (result as Result.Success).data.size)
            coVerify { userApiService.getContacts() }
            coVerify { userDao.getAllUsers() }
        }

    @Test
    fun `saveUsers should save users in the database`() =
        runTest {
            // Arrange
            val users =
                listOf(
                    User(id = 1, name = "John", username = "john123", img = "img.jpg"),
                    User(id = 2, name = "Jane", username = "jane456", img = "img2.jpg"),
                )
            val userEntities = users.map { it.toEntity() }

            coEvery { userDao.insertUsers(userEntities) } returns Unit

            // Act
            userRepository.saveUsers(users)

            // Assert
            coVerify { userDao.insertUsers(userEntities) }
        }

    @Test
    fun `getFavoriteUsers returns success when there are favorite users`() =
        runTest {
            // Arrange
            val favoriteUsers =
                listOf(
                    UserEntity(
                        id = 1,
                        name = "John",
                        username = "john123",
                        img = "img.jpg",
                        isFavorite = true,
                    ),
                    UserEntity(
                        id = 2,
                        name = "Jane",
                        username = "jane456",
                        img = "img2.jpg",
                        isFavorite = true,
                    ),
                )
            val expectedUsers = favoriteUsers.map { it.toDomain() }

            coEvery { userDao.getFavoriteUsers() } returns favoriteUsers

            // Act
            val result = userRepository.getFavoriteUsers()

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(expectedUsers, (result as Result.Success).data)
            coVerify { userDao.getFavoriteUsers() }
        }

    @Test
    fun `getFavoriteUsers returns error if an exception occurs`() =
        runTest {
            // Arrange
            val exception = Exception("Database Error")
            coEvery { userDao.getFavoriteUsers() } throws exception

            // Act
            val result = userRepository.getFavoriteUsers()

            // Assert
            assertTrue(result is Result.Error)
            assertEquals(exception, (result as Result.Error).exception)
        }

    @Test
    fun `toggleFavoriteUser updates the favorite status of a user`() =
        runTest {
            // Arrange
            val userId = 1
            val isFavorite = true
            coEvery { userDao.updateFavoriteStatus(userId, isFavorite) } returns Unit

            // Act
            val result = userRepository.toggleFavoriteUser(userId, isFavorite)

            // Assert
            assertTrue(result is Result.Success)
            coVerify { userDao.updateFavoriteStatus(userId, isFavorite) }
        }

    @Test
    fun `toggleFavoriteUser returns error if an exception occurs`() =
        runTest {
            // Arrange
            val userId = 1
            val isFavorite = true
            val exception = Exception("Database Error")
            coEvery { userDao.updateFavoriteStatus(userId, isFavorite) } throws exception

            // Act
            val result = userRepository.toggleFavoriteUser(userId, isFavorite)

            // Assert
            assertTrue(result is Result.Error)
            assertEquals(exception, (result as Result.Error).exception)
        }
}
