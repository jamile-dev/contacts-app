package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.getUsers()
}
