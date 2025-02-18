package com.picpay.desafio.androd.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val img: String,
    val isFavorite: Boolean = false,
)
