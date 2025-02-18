package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository

class ManageLocalUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun cacheUsers(users: List<User>) {
        userRepository.saveUsers(users)
    }

    suspend fun getCachedUsers(): Result<List<User>> = userRepository.getUsers()

    suspend fun getCachedFavorites(): Result<List<User>> = userRepository.getFavoriteUsers()
}
