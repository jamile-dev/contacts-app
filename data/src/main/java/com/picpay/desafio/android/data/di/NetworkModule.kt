package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.common.Constants.BASE_URL
import com.picpay.desafio.android.data.remote.api.UserApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule =
    module {
        single {
            OkHttpClient.Builder().build()
        }

        single {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single {
            get<Retrofit>().create(UserApiService::class.java)
        }
    }
