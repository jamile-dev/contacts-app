package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule =
    module {
        single {
            AppDatabase.createDatabase(get())
        }

        single {
            get<AppDatabase>().userDao()
        }
    }
