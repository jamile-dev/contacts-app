package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.repository.UserRepository

class FavoriteUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        userId: Int,
        isFavorite: Boolean,
    ) = userRepository.toggleFavoriteUser(userId, isFavorite)
}
