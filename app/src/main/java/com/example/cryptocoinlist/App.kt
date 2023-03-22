package com.example.cryptocoinlist

import android.app.Application
import com.example.cryptocoinlist.di.networkModule
import com.example.cryptocoinlist.di.repositoryModule
import com.example.cryptocoinlist.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR )
            androidContext(this@App)
            modules(
                listOf(
                    viewModelsModule,
                    repositoryModule,
                    networkModule
                )
            )
        }
    }
}