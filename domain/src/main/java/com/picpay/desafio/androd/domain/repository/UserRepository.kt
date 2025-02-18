package com.picpay.desafio.androd.domain.repository

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>

    suspend fun saveUsers(users: List<User>)

    suspend fun searchUsers(query: String): Result<List<User>>

    suspend fun toggleFavoriteUser(
        userId: Int,
        isFavorite: Boolean,
    ): Result<Unit>

    suspend fun getFavoriteUsers(): Result<List<User>>

    suspend fun getUserById(id: Int): Result<User>
}
