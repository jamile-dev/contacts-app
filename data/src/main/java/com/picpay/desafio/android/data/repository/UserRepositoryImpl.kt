package com.picpay.desafio.android.data.repository

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository
import com.picpay.desafio.android.data.database.UserDAO
import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.mapper.toEntity
import com.picpay.desafio.android.data.mapper.toUsers
import com.picpay.desafio.android.data.remote.api.UserApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApiService,
    private val userDao: UserDAO,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> =
        withContext(ioDispatcher) {
            runCatching {
                val apiUsers = api.getContacts().map { it.toDomain() }
                val localUsersMap = userDao.getAllUsers().associateBy { it.id }
                val usersToSave =
                    apiUsers.map { apiUser ->
                        val isFavorite = localUsersMap[apiUser.id]?.isFavorite ?: false
                        apiUser.copy(isFavorite = isFavorite)
                    }
                userDao.insertUsers(usersToSave.map { it.toEntity() })
                usersToSave
            }.recoverCatching {
                val localUsers = userDao.getAllUsers()
                if (localUsers.isNotEmpty()) {
                    localUsers.toUsers()
                } else {
                    throw it
                }
            }.fold(
                onSuccess = { Result.Success(it) },
                onFailure = { Result.Error(it) },
            )
        }

    override suspend fun saveUsers(users: List<User>) =
        withContext(ioDispatcher) {
            val userEntities = users.map { it.toEntity() }
            userDao.insertUsers(userEntities)
        }

    override suspend fun searchUsers(query: String): Result<List<User>> =
        withContext(ioDispatcher) {
            runCatching {
                userDao.searchUsers(query).map { it.toDomain() }
            }.fold(
                onSuccess = { Result.Success(it) },
                onFailure = { Result.Error(it) },
            )
        }

    override suspend fun toggleFavoriteUser(
        userId: Int,
        isFavorite: Boolean,
    ): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                userDao.updateFavoriteStatus(userId, isFavorite)
            }.fold(
                onSuccess = { Result.Success(Unit) },
                onFailure = { Result.Error(it) },
            )
        }

    override suspend fun getFavoriteUsers(): Result<List<User>> =
        withContext(ioDispatcher) {
            runCatching {
                userDao.getFavoriteUsers().map { it.toDomain() }
            }.fold(
                onSuccess = { Result.Success(it) },
                onFailure = { Result.Error(it) },
            )
        }

    override suspend fun getUserById(id: Int): Result<User> =
        withContext(ioDispatcher) {
            runCatching {
                userDao.getUserById(id)?.toDomain() ?: throw Exception("User not find")
            }.fold(
                onSuccess = { Result.Success(it) },
                onFailure = { Result.Error(it) },
            )
        }
}
