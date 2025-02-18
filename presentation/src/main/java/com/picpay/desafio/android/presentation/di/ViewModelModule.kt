package com.picpay.desafio.android.presentation.di

import com.picpay.desafio.android.presentation.ui.favorites.FavoritesViewModel
import com.picpay.desafio.android.presentation.ui.home.HomeViewModel
import com.picpay.desafio.android.presentation.ui.userdetail.UserDetailViewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        factory { HomeViewModel(get(), get(), get(), get()) }
        factory { FavoritesViewModel(get(), get()) }
        factory { UserDetailViewModel(get()) }
    }
