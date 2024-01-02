package com.truongvim.snakegame

import android.app.Application
import com.truongvim.snakegame.di.dispatcherModule
import com.truongvim.snakegame.di.networkModule
import com.truongvim.snakegame.di.repositoryModule
import com.truongvim.snakegame.di.useCaseModule
import com.truongvim.snakegame.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule,
                useCaseModule,
                dispatcherModule
            )
        }
    }
}