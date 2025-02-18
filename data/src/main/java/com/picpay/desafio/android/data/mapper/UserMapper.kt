package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.data.database.UserEntity
import com.picpay.desafio.android.data.model.UserDTO

fun UserDTO.toDomain(): User =
    User(
        id = this.id.toInt(),
        name = this.name,
        username = this.username,
        img = this.img,
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.img,
        isFavorite = this.isFavorite,
    )

fun UserEntity.toUser(): User =
    User(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.img,
        isFavorite = this.isFavorite,
    )

fun List<UserEntity>.toUsers(): List<User> = this.map { it.toUser() }
