package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.androd.domain.di.domainModule
import com.picpay.desafio.android.data.di.dataModule
import com.picpay.desafio.android.data.di.databaseModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.data.di.repositoryModule
import com.picpay.desafio.android.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(androidContext = this@App)
            modules(
                repositoryModule,
                networkModule,
                domainModule,
                viewModelModule,
                databaseModule,
                dataModule,
            )
        }
    }
}
