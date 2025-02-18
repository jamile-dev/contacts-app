package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: Int): Result<User> = userRepository.getUserById(id)
}
