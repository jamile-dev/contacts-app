package com.picpay.desafio.android.data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("img") val img: String,
)
