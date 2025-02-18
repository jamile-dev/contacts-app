package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository

class SaveUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(users: List<User>) = userRepository.saveUsers(users)
}
