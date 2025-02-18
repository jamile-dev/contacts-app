package com.picpay.desafio.android.data.di

import com.picpay.desafio.androd.domain.repository.UserRepository
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val dataModule =
    module {
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
    }
