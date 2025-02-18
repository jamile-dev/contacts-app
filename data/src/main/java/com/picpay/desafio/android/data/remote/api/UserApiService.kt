package com.picpay.desafio.android.data.remote.api

import com.picpay.desafio.android.data.model.UserDTO
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getContacts(): List<UserDTO>
}
