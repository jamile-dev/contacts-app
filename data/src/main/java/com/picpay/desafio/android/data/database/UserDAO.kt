package com.picpay.desafio.android.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    suspend fun getFavoriteUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE name LIKE :query OR username LIKE :query")
    suspend fun searchUsers(query: String): List<UserEntity>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET isFavorite = :isFavorite WHERE id = :userId")
    suspend fun updateFavoriteStatus(
        userId: Int,
        isFavorite: Boolean,
    )

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?
}
