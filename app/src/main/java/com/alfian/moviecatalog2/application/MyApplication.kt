package com.alfian.moviecatalog2.application

import android.app.Application
import com.alfian.moviecatalog2.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(
                remoteModule, repositoryModule,
                showViewModelModule, detailViewModelModule, networkModule))
        }
    }
}