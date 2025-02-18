package com.picpay.desafio.androd.domain.usecase

import com.picpay.desafio.androd.domain.model.User

class FilterUsersUseCase {
    fun filterUsers(
        users: List<User>,
        query: String,
    ): List<User> =
        if (query.isEmpty()) {
            users
        } else {
            users.filter { it.name.contains(query, ignoreCase = true) }
        }
}
