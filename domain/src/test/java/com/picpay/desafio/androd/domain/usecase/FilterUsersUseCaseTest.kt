package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterUsersUseCaseTest {
    private val useCase = FilterUsersUseCase()

    @Test
    fun `filterUsers should return all users when query is empty`() {
        // Arrange
        val users =
            listOf(
                User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
            )
        val query = ""

        // Act
        val result = useCase.filterUsers(users, query)

        // Assert
        assertEquals(users, result)
    }

    @Test
    fun `filterUsers should return filtered users when query is not empty`() {
        // Arrange
        val users =
            listOf(
                User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
            )
        val query = "Jane"

        // Act
        val result = useCase.filterUsers(users, query)

        // Assert
        assertEquals(listOf(users[1]), result)
    }

    @Test
    fun `filterUsers should return empty list when no users match the query`() {
        // Arrange
        val users =
            listOf(
                User(id = 1, name = "John Doe", username = "johndoe", img = "img_url"),
                User(id = 2, name = "Jane Smith", username = "janesmith", img = "img_url"),
            )
        val query = "Alice"

        // Act
        val result = useCase.filterUsers(users, query)

        // Assert
        assertEquals(emptyList<User>(), result)
    }
}
