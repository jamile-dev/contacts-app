package com.picpay.desafio.androd.domain.di

import com.picpay.desafio.androd.domain.usecase.FavoriteUsersUseCase
import com.picpay.desafio.androd.domain.usecase.FilterUsersUseCase
import com.picpay.desafio.androd.domain.usecase.GetUserByIdUseCase
import com.picpay.desafio.androd.domain.usecase.GetUsersUseCase
import com.picpay.desafio.androd.domain.usecase.ManageLocalUsersUseCase
import com.picpay.desafio.androd.domain.usecase.SaveUsersUseCase
import org.koin.dsl.module

val domainModule =
    module {
        factory { SaveUsersUseCase(get()) }
        factory { ManageLocalUsersUseCase(get()) }
        factory { FavoriteUsersUseCase(get()) }
        factory { GetUsersUseCase(get()) }
        factory { FilterUsersUseCase() }
        factory { GetUserByIdUseCase(get()) }
    }
