package com.picpay.desafio.android.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.androd.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val img: String,
    val isFavorite: Boolean = false,
) {
    fun toDomain(): User =
        User(
            id = this.id,
            name = this.name,
            username = this.username,
            img = this.img,
            isFavorite = this.isFavorite,
        )
}
